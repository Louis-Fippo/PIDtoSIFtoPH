package de.bioquant.cytoscape.pidfileconverter.FileWriter;

import java.io.FileNotFoundException;

import de.bioquant.cytoscape.pidfileconverter.NodeManager.NodeManagerImpl;
//import cytoscape.CyNetwork;

public interface FileWriter {
	public void write(String path, NodeManagerImpl manager) throws FileNotFoundException;
	
 
}


