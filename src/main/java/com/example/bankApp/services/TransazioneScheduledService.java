package com.example.bankApp.services;

import com.example.bankApp.domain.dto.requests.TransazioneRequest;
import com.example.bankApp.domain.dto.requests.TransazioneScheduledRequest;
import com.example.bankApp.domain.dto.requests.TransazioneScheduledUpdateRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.entities.TransazioneScheduled;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.IllegalTransactionException;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.repositories.TransazioneScheduledRepository;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TransazioneScheduledService implements Job {

    @Autowired
    private TransazioneScheduledRepository transazioneScheduledRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ContoService contoService;
    @Autowired
    private TransazioneService transazioneService;
    @Autowired
    private Scheduler scheduler;

    public EntityIdResponse createTransazioneScheduled(TransazioneScheduledRequest request)
            throws MyEntityNotFoundException, IllegalTransactionException, SchedulerException {
        // Verifico che l'utente esista e lo prendo
        Utente utente = utenteService.getById(request.id_utente());
        // Verifico che i due conti esistano e li prendo
        Conto contoMittente = contoService.getById(request.id_mittente());
        Conto contoDestinatario = contoService.getById(request.id_destinatario());
        // verifico che i conti siano distinti
        if (contoMittente.equals(contoDestinatario)) {
            throw new IllegalTransactionException("Conto mittente e destinatario coincidono!");
        }
        // Verifico che il conto mittente appartenga all'utente
        if (!contoMittente.getIntestatari().contains(utente)) {
            throw new IllegalTransactionException("Il conto " + contoMittente.getId() + " non appartiene all'utente " +
                    utente.getId());
        }
        TransazioneScheduled transazioneScheduled = TransazioneScheduled
                .builder()
                .amount(request.amount())
                .publishTime(request.publishTime())
                .utente(utente)
                .contoMittente(contoMittente)
                .contoDestinatario(contoDestinatario)
                .build();

        transazioneScheduledRepository.save(transazioneScheduled);

        TransazioneRequest transazioneRequest = TransazioneRequest
                .builder()
                .amount(request.amount())
                .id_utente(utente.getId())
                .id_mittente(contoMittente.getId())
                .id_destinatario(contoDestinatario.getId())
                .tipoOperazione("transazione")
                .build();
        // crea il job per lo schedule della transazione
        JobDetail jobDetail = buildJobDetail(transazioneScheduled, transazioneRequest);
        Trigger trigger = buildJobTrigger(jobDetail, Date.from(transazioneScheduled.getPublishTime().atZone(ZoneId.systemDefault()).toInstant()));
        scheduler.scheduleJob(jobDetail, trigger);
        return EntityIdResponse.builder().id(transazioneScheduled.getId()).build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, Date publishTime) {

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .startAt(publishTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();

    }

    private JobDetail buildJobDetail(TransazioneScheduled transazioneScheduled,
                                     TransazioneRequest transazioneRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("entityData", transazioneRequest); // ---> l'entità che passerò all'execute
        jobDataMap.put("id", transazioneScheduled.getId()); // ---> l'id del job
        return JobBuilder
                .newJob(TransazioneScheduledService.class)
                .withIdentity(String.valueOf(transazioneScheduled.getId()), "transazioni")
                .storeDurably()
                .setJobData(jobDataMap)
                .build();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        TransazioneRequest request = (TransazioneRequest) jobDataMap.get("entityData");
        Long id_scheduled = jobDataMap.getLongValue("id");
        try {
            transazioneService.createTransazione(request);
        } catch (MyEntityNotFoundException | IllegalTransactionException e) {
            throw new RuntimeException(e);
        }
        transazioneScheduledRepository.deleteById(id_scheduled);
    }

    public EntityIdResponse updateTransazioneScheduled(Long id, TransazioneScheduledUpdateRequest request) throws SchedulerException {
        TransazioneScheduled transazioneScheduled = transazioneScheduledRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("La transazione schedulata con " + id + " non è presente"));
        JobKey jobKey = new JobKey(String.valueOf(transazioneScheduled.getId()), "transazioni");
        scheduler.deleteJob(jobKey);
        TransazioneScheduledRequest transazioneScheduledRequest = TransazioneScheduledRequest
                .builder()
                .amount(request.amount() == null ? transazioneScheduled.getAmount() : request.amount())
                .publishTime(request.publishTime() == null ? transazioneScheduled.getPublishTime() : request.publishTime())
                .id_utente(transazioneScheduled.getUtente().getId())
                .id_mittente(transazioneScheduled.getContoMittente().getId())
                .id_destinatario(transazioneScheduled.getContoDestinatario().getId())
                .build();
        transazioneScheduledRepository.deleteById(id);
        return createTransazioneScheduled(transazioneScheduledRequest);
    }

    public void deleteTransazioneScheduledById(Long id) throws SchedulerException {
        TransazioneScheduled transazioneScheduled = transazioneScheduledRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("La transazione schedulata con " + id + " non è presente"));
        JobKey jobKey = new JobKey(String.valueOf(transazioneScheduled.getId()), "transazioni");
        scheduler.deleteJob(jobKey);
        transazioneScheduledRepository.deleteById(id);
    }

}
