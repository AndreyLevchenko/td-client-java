//
// Java Client Library for Treasure Data Cloud
//
// Copyright (C) 2011 - 2012 Muga Nishizawa
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package com.treasure_data.client;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.treasure_data.auth.TreasureDataCredentials;
import com.treasure_data.model.AuthenticateRequest;
import com.treasure_data.model.AuthenticateResult;
import com.treasure_data.model.CreateDatabaseRequest;
import com.treasure_data.model.CreateDatabaseResult;
import com.treasure_data.model.CreateTableRequest;
import com.treasure_data.model.CreateTableResult;
import com.treasure_data.model.Database;
import com.treasure_data.model.DeleteDatabaseRequest;
import com.treasure_data.model.DeleteDatabaseResult;
import com.treasure_data.model.DeleteTableRequest;
import com.treasure_data.model.DeleteTableResult;
import com.treasure_data.model.ExportRequest;
import com.treasure_data.model.ExportResult;
import com.treasure_data.model.ImportRequest;
import com.treasure_data.model.ImportResult;
import com.treasure_data.model.Job;
import com.treasure_data.model.JobResult;
import com.treasure_data.model.KillJobRequest;
import com.treasure_data.model.KillJobResult;
import com.treasure_data.model.ListDatabasesRequest;
import com.treasure_data.model.ListDatabasesResult;
import com.treasure_data.model.ListJobsRequest;
import com.treasure_data.model.ListJobsResult;
import com.treasure_data.model.ListTablesRequest;
import com.treasure_data.model.ListTablesResult;
import com.treasure_data.model.GetJobResultRequest;
import com.treasure_data.model.GetJobResultResult;
import com.treasure_data.model.ServerStatus;
import com.treasure_data.model.ServerStatusRequest;
import com.treasure_data.model.ServerStatusResult;
import com.treasure_data.model.ShowJobRequest;
import com.treasure_data.model.ShowJobResult;
import com.treasure_data.model.SubmitJobRequest;
import com.treasure_data.model.SubmitJobResult;
import com.treasure_data.model.Table;

public class TreasureDataClient {
    private static Logger LOG = Logger.getLogger(TreasureDataClient.class.getName());

    /**
     * adaptor factory method
     */
    static ClientAdaptor createClientAdaptor(
	    TreasureDataCredentials credentials, Properties props) {
	Config conf = new Config();
	conf.setCredentials(credentials);
	ClientAdaptor clientAdaptor = new HttpClientAdaptor(conf);
	return clientAdaptor;
    }

    private ClientAdaptor clientAdaptor;

    public TreasureDataClient() {
        this(System.getProperties());
    }

    public TreasureDataClient(Properties props) {
        this(new TreasureDataCredentials(props), props);
    }

    public TreasureDataClient(TreasureDataCredentials credentials, Properties props) {
	clientAdaptor = createClientAdaptor(credentials, props);
    }

    public void setTreasureDataCredentials(TreasureDataCredentials credentials) {
        clientAdaptor.setTreasureDataCredentials(credentials);
    }

    public void authenticate(String email, String password)
            throws ClientException {
        AuthenticateResult result = clientAdaptor.authenticate(
                new AuthenticateRequest(email, password));
        TreasureDataCredentials credentials =  result.getTreasureDataCredentials();
        setTreasureDataCredentials(credentials);
    }
    public AuthenticateResult authenticate(AuthenticateRequest request)
            throws ClientException {
        return clientAdaptor.authenticate(request);
    }

    // Server Status API

    public ServerStatus getServerStatus() throws ClientException {
        return clientAdaptor.getServerStatus(
                new ServerStatusRequest()).getServerStatus();
    }

    public ServerStatusResult getServerStatus(ServerStatusRequest request)
            throws ClientException {
        return clientAdaptor.getServerStatus(new ServerStatusRequest());
    }

    // Database API

    public List<Database> listDatabases() throws ClientException {
        return listDatabases(new ListDatabasesRequest()).getDatabases();
    }

    public ListDatabasesResult listDatabases(ListDatabasesRequest request)
            throws ClientException {
        return clientAdaptor.listDatabases(request);
    }

    public Database createDatabase(String databaseName)
            throws ClientException {
        return createDatabase(
                new CreateDatabaseRequest(databaseName)).getDatabase();
    }

    public CreateDatabaseResult createDatabase(CreateDatabaseRequest request)
            throws ClientException {
	return clientAdaptor.createDatabase(request);
    }

    public void deleteDatabase(String databaseName) throws ClientException {
        deleteDatabase(new DeleteDatabaseRequest(new Database(databaseName)));
    }

    public DeleteDatabaseResult deleteDatabase(DeleteDatabaseRequest request)
            throws ClientException {
	return clientAdaptor.deleteDatabase(request);
    }

    // Table API

    public List<Table> listTables(String databaseName)
            throws ClientException {
        return listTables(new Database(databaseName));
    }

    public List<Table> listTables(Database database)
            throws ClientException {
        return listTables(new ListTablesRequest(database)).getTables();
    }

    public ListTablesResult listTables(ListTablesRequest request)
            throws ClientException {
	return clientAdaptor.listTables(request);
    }

    public Table createTable(String databaseName, String tableName)
            throws ClientException {
        return createTable(new Database(databaseName), tableName);
    }

    public Table createTable(Database database, String tableName)
            throws ClientException {
        CreateTableResult result = createTable(new CreateTableRequest(
                database, tableName));
        return result.getTable();
    }

    public CreateTableResult createTable(CreateTableRequest request)
            throws ClientException {
	return clientAdaptor.createTable(request);
    }

    // TODO #MN add it in next version
    //public TableDescription describeTable(DescribeTableRequest request) throws ClientException;

    // TODO #MN add it in next version
    /**
    TableDescription describeTable(String databaseName, String tableName) throws ClientException {
        return describeTable(new DescribeTableRequest(databaseName, tableName));
    }
    */

    public void deleteTable(String databaseName, String tableName)
            throws ClientException {
        deleteTable(new DeleteTableRequest(new Table(
                new Database(databaseName), tableName, Table.Type.LOG)));
    }

    public DeleteTableResult deleteTable(DeleteTableRequest request)
            throws ClientException {
	return clientAdaptor.deleteTable(request);
    }

    // TODO #MN add it in next version
    //TableSchema updateTableSchema(UpdateTableSchemaRequest request) throws ClientException;

    // Import API

    public ImportResult importData(ImportRequest request) throws ClientException {
        return clientAdaptor.importData(request);
    }

    // Export API

    public ExportResult exportData(ExportRequest request) throws ClientException {
        return clientAdaptor.exportData(request);
    }

    // Job API

    public void submitJob(Job job) throws ClientException {
        submitJob(new SubmitJobRequest(job));
    }

    public SubmitJobResult submitJob(SubmitJobRequest request)
            throws ClientException {
        return clientAdaptor.submitJob(request);
    }

    public List<Job> listJobs(long from, long to) throws ClientException {
        return listJobs(new ListJobsRequest(from, to)).getJobs();
    }

    public ListJobsResult listJobs(ListJobsRequest request) throws ClientException {
        return clientAdaptor.listJobs(request);
    }

    public void killJob(String jobID) throws ClientException {
        killJob(new Job(jobID));
    }

    public void killJob(Job job) throws ClientException {
        killJob(new KillJobRequest(job));
    }

    public KillJobResult killJob(KillJobRequest request) throws ClientException {
        return clientAdaptor.killJob(request);
    }

    public void showJob(String jobID) throws ClientException {
        showJob(new Job(jobID));
    }

    public void showJob(Job job) throws ClientException {
        showJob(new ShowJobRequest(job));
    }

    public ShowJobResult showJob(ShowJobRequest request) throws ClientException {
        return clientAdaptor.showJob(request);
    }

    // TODO #MN add it in next version
    //JobDescription describeJob(DescribeJobRequest request) throws ClientException;

    public JobResult getJobResult(Job job) throws ClientException {
        return getJobResult(new GetJobResultRequest(
                new JobResult(job))).getJobResult();
    }

    public GetJobResultResult getJobResult(GetJobResultRequest request) throws ClientException {
        return clientAdaptor.getJobResult(request);
    }

    // Job Scheduling API

    // TODO #MN add it in next version
    //ListScheduledJobResult listJobSchedules(ListScheduledJobRequest request) throws ClientException; // JobScheduleSummary < JobSchedule

    // TODO #MN add it in next version
    //List<JobSchedule> listJobSchedules() throws ClientException;

    // TODO #MN add it in next version
    //JobSchedule createJobSchedule(CreateJobScheduleRequest request) throws ClientException;

    // TODO #MN add it in next version
    //void deleteJobSchedule(DeleteJobScheduleRequest request) throws ClientException;

    // TODO #MN add it in next version
    //void deleteJobSchedule(String jobScheduleName) throws ClientException;
}
