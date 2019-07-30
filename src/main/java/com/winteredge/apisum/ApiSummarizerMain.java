package com.winteredge.apisum;

import picocli.CommandLine;

@CommandLine.Command(
        name="summarize",
        footer = "Copyright(c) 2019.  Mayo Foundation for Medical Education and Research.",
        description = "Takes a OpenAPI 3 YAML file and produces a summary in HTML format.",
        subcommands = {
                GenerateTableSubcommand.class
        },
        synopsisSubcommandLabel="COMMAND"
)
public class ApiSummarizerMain implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new ApiSummarizerMain());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
