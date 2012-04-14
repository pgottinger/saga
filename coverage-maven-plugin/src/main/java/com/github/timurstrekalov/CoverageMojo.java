package com.github.timurstrekalov;

import com.google.common.collect.ImmutableList;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

/**
 * @goal coverage
 * @phase verify
 * @threadSafe
 */
public class CoverageMojo extends AbstractMojo {

    /**
     * @parameter
     * @required
     */
    private File baseDir;

    /**
     * @description A comma-separated list of Ant-style paths to tests to be run
     * @parameter
     * @required
     */
    private String includes;

    /**
     * @description A comma-separated list of Ant-style paths to exclude from the test run
     * @parameter
     */
    private String excludes;

    /**
     * @parameter
     * @required
     */
    private File outputDir;

    /**
     * @parameter
     */
    private Boolean outputInstrumentedFiles;

    /**
     * @parameter
     */
    private String[] noInstrumentPatterns;

    /**
     * @parameter
     */
    private Boolean cacheInstrumentedCode;

    /**
     * @parameter
     */
    private OutputStrategy outputStrategy;

    /**
     * @parameter
     */
    private Integer threadCount;

    public void execute() throws MojoExecutionException {
        final CoverageGenerator gen = new CoverageGenerator(baseDir, includes, excludes, outputDir);

        gen.setOutputInstrumentedFiles(outputInstrumentedFiles);
        gen.setCacheInstrumentedCode(cacheInstrumentedCode);
        gen.setNoInstrumentPatterns(ImmutableList.copyOf(noInstrumentPatterns));
        gen.setOutputStrategy(outputStrategy);
        gen.setThreadCount(threadCount);

        try {
            gen.run();
        } catch (final IOException e) {
            throw new MojoExecutionException("Error generating coverage", e);
        }
    }

}
