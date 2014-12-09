package de.bioquant.cytoscape.pidfileconverter.ActionParameter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class ListeAction {
	
	
	//les attributs
	ArrayList<ActionParameter> listeAction = new ArrayList<ActionParameter>();
	Hashtable<String, ArrayList<ActionParameter>> mesures = new Hashtable<String, ArrayList<ActionParameter>>();
	String fichierListeAction;
	
	 
	 //constructor
	public ListeAction(String fichier){
	
		this.fichierListeAction =fichier;
		this.mesures = geneMesures(fichier);
	}
	
	
	
	//méthodes 

	//for build the list of action parameter
	ArrayList<ActionParameter> actionList(String listeA)
	{
		ArrayList<ActionParameter> acParam =new ArrayList<ActionParameter>();
		String [] tabSousAction=listeA.split(";");
		String [] tableauParam = new String[5];
		float rate;
		
		
		for(int i=0; i<tabSousAction.length;i++)
		{
			
			StringTokenizer st = new StringTokenizer(tabSousAction[i], " \t\n\r\f@~[]");
			 
			
			
			int j=0;
			while (st.hasMoreElements()) {
				
				tableauParam[j]=st.nextElement().toString();
				
				j++;
								
			}
			
			//on insere les valeurs dans l'objet
			rate=1/Float.parseFloat(tableauParam[3]);
			ActionParameter ap =new ActionParameter(Integer.parseInt(tableauParam[1]),Integer.parseInt(tableauParam[2]),tableauParam[0],rate,Integer.parseInt(tableauParam[4]));
			acParam.add(ap);
			
		}
		
		
		return acParam;
	}
	
	//for build the table of mesures genes 
	
	Hashtable<String, ArrayList<ActionParameter>> geneMesures(String fichierTemp){
		
		Hashtable<String, ArrayList<ActionParameter>> mesuresTemp = new Hashtable<String, ArrayList<ActionParameter>>();
		String strLine; 
		String [] sousChaine1;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fichierTemp);
	        DataInputStream in= new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader (in));
	        br.readLine();
	        while((strLine = br.readLine()) != null)
	        {
	        	System.out.println("une ligne"+strLine);
	        	sousChaine1=strLine.split(":");
	        	mesuresTemp.put(sousChaine1[0], actionList(sousChaine1[1]));
	        }
	        
	        in.close();
	        br.close();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		return mesuresTemp;
	}
	
	public Hashtable<String, ArrayList<ActionParameter>> getGeneMesures(){
		
		return this.mesures;
	}
	
	
	


}
