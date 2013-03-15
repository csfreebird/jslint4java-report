package org.freebird;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
import java.io.File;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import java.util.Locale;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.project.MavenProject;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 *
 * @goal my-report
 *
 * @phase site
 */
public class Jslint4JavaReport extends AbstractMavenReport {

    /**
     * Describe variable <code>resultFile</code> here.
     *
     * @parameter
     */
    private File resultFile;

    private Renderer siteRenderer;
    
    @Override
    protected Renderer getSiteRenderer() {
        return siteRenderer;
    } 

    @Override
    protected String getOutputDirectory() {
        return "${project.build.directory}/site/myreport";
    }
    
    private MavenProject project;

    @Override
    protected MavenProject getProject() {
        return project;
    }


    /**
     * Describe <code>executeReport</code> method here.
     *
     * @param locale a <code>Locale</code> value
     * @exception MavenReportException if an error occurs
     */
    @Override
    protected void executeReport(Locale locale) throws MavenReportException {
        Sink sink = getSink();

	if(!resultFile.exists()) {
	    throw new MavenReportException("resultFile doesn't exit! Please check your configuration of jslint4java-report plugin");
	}
	if(!resultFile.canRead()) {
	    throw new MavenReportException("can't read resultFile for jslint4java-report plugin");
	}
	
	FileReader reader = null;
	BufferedReader br = null;
	try {
	    reader = new FileReader(resultFile);
	    br = new BufferedReader(reader);
	    String row;
	    while((row = br.readLine()) != null) {
		sink.paragraph();
		sink.text(row);
		sink.paragraph();
	    }
	} catch(Exception ex) {
	    throw new MavenReportException("Got exception when reading the result file in jslint4java-report plugin",ex);
	} finally {
	    try{
		if(br != null) {
		    br.close();
		}

		if(reader != null) {
		    reader.close();
		}
	    }catch(IOException ex){
		getLog().error(ex.getMessage(),ex);
	    }
	}
    }

    /**
     * Describe <code>getOutputName</code> method here.
     *
     * @return a <code>String</code> value
     */
    public String getOutputName() {
        return "jslint4java-report";
    }

    /**
     * Describe <code>getName</code> method here.
     *
     * @param locale a <code>Locale</code> value
     * @return a <code>String</code> value
     */
    public String getName(Locale locale) {
        return "jslint4java";
    }

    /**
     * Describe <code>getDescription</code> method here.
     *
     * @param locale a <code>Locale</code> value
     * @return a <code>String</code> value
     */
    public String getDescription(Locale locale) {
        return "The check result of java script codes using jslint4java";
    }
}
