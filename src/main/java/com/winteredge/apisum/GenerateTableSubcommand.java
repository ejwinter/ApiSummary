package com.winteredge.apisum;

import com.winteredge.apisum.entitytablesummary.ApiSummary;
import com.winteredge.apisum.entitytablesummary.EntitySummarizer;
import com.winteredge.apisum.entitytablesummary.EntitySummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(
        name="to-table",
        description = "Takes a OpenAPI 3 YAML file and produces a summary in HTML format."
)
public class GenerateTableSubcommand implements Runnable{

    private static final Logger logger = LogManager.getLogger(GenerateTableSubcommand.class);

    private final EntitySummarizer entitySummarizer = new EntitySummarizer();

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(
            names = {"--def-file", "-f"},
            description = "The OpenAPI 3 YAML file we want to summarize.",
            required = true
    )
    private Path path;

    @CommandLine.Option(
            names = {"--def-file", "-f"},
            description = "The OpenAPI 3 YAML file we want to summarize.",
            required = true
    )
    private Path output;

    @Override
    public void run() {
        logger.info("Summarizing {}", path);

        if(!Files.isRegularFile(path)){
            throw new CommandLine.ParameterException(spec.commandLine(), "The given path does not exist or is not a file.: " + path);
        }

        try {
            ApiSummary summary = entitySummarizer.summarize(path.toFile());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
