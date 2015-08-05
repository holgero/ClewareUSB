package de.holger_oehm.xfd;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public interface MonitorInterface {

    BuildState state() throws ClientProtocolException, IOException;

}