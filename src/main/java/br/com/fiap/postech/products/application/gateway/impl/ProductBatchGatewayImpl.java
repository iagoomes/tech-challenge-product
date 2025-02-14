package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.ProductBatchGateway;
import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.exception.JobProcessingException;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductBatchGatewayImpl implements ProductBatchGateway {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public ProductCsvUploadResponse startBatchJob(LoadProduct loadProduct) {
        try {
            JobParameters jobParameters = new JobParameters();
            jobLauncher.run(job, jobParameters);

            ProductCsvUploadResponse response = new ProductCsvUploadResponse();
            response.setMessage("Job started");
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JobProcessingException("Error processing job");
        }
    }
}