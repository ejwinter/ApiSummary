package com.winteredge.apisum;

import com.winteredge.apisum.entitytablesummary.ApiSummary;
import com.winteredge.apisum.entitytablesummary.EntitySummarizer;
import com.winteredge.apisum.entitytablesummary.EntitySummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import javax.swing.text.TabSet;
import java.io.*;
import java.util.Collections;

@CommandLine.Command(
        name="summarize",
        version = "1.00.00",
        footer = "Copyright(c) 2019.  Mayo Foundation for Medical Education and Research.",
        description = "Takes a OpenAPI 3 YAML file and produces a summary in HTML format.",
        subcommands = {
                ApiSummarizerMain.TableSummary.class
        },
        synopsisSubcommandLabel="COMMAND"
)
public class ApiSummarizerMain implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new ApiSummarizerMain());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }


    @CommandLine.Command(
            name = "to-table",
            aliases = "tt",
            description = "Convert a OpenApi3 model to a tabular format."
    )
    public static class TableSummary implements Runnable{

        private static final Logger logger = LoggerFactory.getLogger(TableSummary.class);

        PrintStream out = System.out;

        @CommandLine.Option(
                names = {"-i", "--input"},
                description = "The input OpenAPI 3 file in YAML format.",
                required = true
        )
        private File apiYamlFile;

        @CommandLine.Option(
                names = {"-o", "--output"},
                description = "The location to a file that we will replace with the HTML output file.",
                required = true
        )
        private File outputFile;

        @CommandLine.Option(
                names = {"-r", "--replace"},
                description = "Set this flag if the ouput file should be replaced.",
                defaultValue = "false"
        )
        private Boolean overwrite = false;

        @CommandLine.Spec
        CommandLine.Model.CommandSpec spec;

        @Override
        public void run() {
            if(!apiYamlFile.exists()){
                out.println("The provided input file does not exist: " + apiYamlFile.getAbsolutePath());
                System.exit(1);
            }

            if(!overwrite && outputFile.exists()){
                out.println("The provided output file already exists and you have not chosen to overwrite it.");
                System.exit(1);
            }else if(outputFile.exists()){
                outputFile.delete();
            }

            try {
                ApiSummary summary = new EntitySummarizer().summarize(apiYamlFile);
                TemplateResolver templateResolver = new TemplateResolver("TableTemplate", new InputStreamReader(getClass().getResourceAsStream("/summaryTemplate.ftl")));

                if(overwrite){

                }
                try(FileOutputStream outputStream = new FileOutputStream(outputFile, false)){
                    templateResolver.resolve(Collections.singletonMap("root", summary), new OutputStreamWriter(outputStream));
                }
            } catch (Exception e) {
                logger.error("We could not parse the provided OpenAPI3 YAML file: " + apiYamlFile.getAbsolutePath(), e);
                out.println("We could not parse the provided OpenAPI3 YAML file: " + apiYamlFile.getAbsolutePath());
            }

        }
    }
}
