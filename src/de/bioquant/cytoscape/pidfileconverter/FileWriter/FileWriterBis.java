package de.bioquant.cytoscape.pidfileconverter.FileWriter;


import java.io.FileNotFoundException;
import cytoscape.CyNetwork;

public interface FileWriterBis {
	
	public void writeBis(String path, CyNetwork Net) throws FileNotFoundException;

}





