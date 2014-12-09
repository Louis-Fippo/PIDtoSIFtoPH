package de.bioquant.cytoscape.pidfileconverter.FileWriter;

import giny.view.NodeView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import cytoscape.data.CyAttributes;
import cytoscape.view.CyNetworkView;
import cytoscape.CyEdge;
import cytoscape.CyNode;
import cytoscape.CyNetwork; 
import cytoscape.Cytoscape;
import de.bioquant.cytoscape.pidfileconverter.Exceptions.InvalidInteractionIdException;
import de.bioquant.cytoscape.pidfileconverter.Model.InteractionComponent;
import de.bioquant.cytoscape.pidfileconverter.Model.InteractionNode;
import de.bioquant.cytoscape.pidfileconverter.Model.MoleculeNode;
import de.bioquant.cytoscape.pidfileconverter.Model.NodeOfPattern;
import de.bioquant.cytoscape.pidfileconverter.Model.PathwayNode;
import de.bioquant.cytoscape.pidfileconverter.Model.Pattern;
import de.bioquant.cytoscape.pidfileconverter.Naming.CreatorUniprotWithModification;
import de.bioquant.cytoscape.pidfileconverter.Naming.NameCreator;
import de.bioquant.cytoscape.pidfileconverter.NodeManager.InteractionNodeManager;
import de.bioquant.cytoscape.pidfileconverter.NodeManager.NodeManagerImpl;
import de.bioquant.cytoscape.pidfileconverter.Ontology.Ontology;
import de.bioquant.cytoscape.pidfileconverter.Ontology.OntologyElement;
import de.bioquant.cytoscape.pidfileconverter.Ontology.OntologyManager;
import de.bioquant.cytoscape.pidfileconverter.Ontology.Exceptions.InconsistentOntologyException;
import de.bioquant.cytoscape.pidfileconverter.Ontology.Exceptions.UnknownOntologyException;
import de.bioquant.cytoscape.pidfileconverter.Ontology.Specialized.EdgeTypeOntology;
import de.bioquant.cytoscape.pidfileconverter.ActionParameter.*;

public final class phFileWriter implements FileWriter,FileWriterBis {
	
	private static phFileWriter instance = null;
	private NameCreator naming = CreatorUniprotWithModification.getInstance();
	//private Map<String, InteractionNode> interactions = new HashMap<String, InteractionNode>(); //les noeuds d'interactions pour le parcours des graphes
    private Collection<MoleculeNode> geneMesures;
    private ArrayList<CyNode> geneMesuresCy = new ArrayList<CyNode>();
    
    
    
	private phFileWriter() {
	}

	public static phFileWriter getInstance() {
		if (instance == null)
			instance = new phFileWriter();
		return instance;
	}

	@Override
	public void write(String path, NodeManagerImpl manager)
			throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		
		try {
			
			this.writeInteractions(writer, manager);
			//	this.detectInteractionPatterns(intCompManager, interactionManager);
		} catch (InconsistentOntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownOntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer.close();
		

	}
	
	public void writeBis(String path, CyNetwork Net) throws FileNotFoundException{
		
		PrintWriter writer= new PrintWriter(path);
		//FileWriter writerBis=new FileWriter(path);
		
		try{
			
			
			this.writePHfromCyto(writer, Net);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		writer.close();
	}
	
		
	public void writeInteractions(PrintWriter out,
			InteractionNodeManager manager)
			throws InconsistentOntologyException, UnknownOntologyException {
		Collection<InteractionNode> interactions = manager.getAllInteractions();
		for (InteractionNode inter : interactions) {

			try {
				String interID = naming.getNameForInteraction(inter);
				String interPid = inter.getFullPid();

				Ontology onto = OntologyManager.getInstance().getOntology(
						EdgeTypeOntology.NAME);
				if (onto != null) {
					if (onto.getClass() == EdgeTypeOntology.class) {

						EdgeTypeOntology eOnto = (EdgeTypeOntology) onto;

						Collection<InteractionComponent> intComponents = inter
								.getInteractionComponents();
						for (InteractionComponent node : intComponents) {
							String component = naming
									.getNameForCompMolMember(node);
							Collection<OntologyElement> roles;

							roles = node.getRolesTypeForInteraction(interPid);

							for (OntologyElement role : roles) {
								String roleString = role.getName();
								if (eOnto.isSpecialEdge(role, EdgeTypeOntology.INCOMINGNAME))
									TupelWriter.printTriple(out, component,
											roleString, interID);
								else
									TupelWriter.printTriple(out, interID,
											roleString, component);

							}

						}
					}
				}

				Collection<PathwayNode> pathways = inter.getPatways();
				for (PathwayNode pathway : pathways) {
					String right = naming.getNameForPathway(pathway);
					String middle = "hasPathway";
					String left = interID;
					TupelWriter.printTriple(out, left, middle, right);
				}

				if (inter.hasPosCondition()) {
					String left = inter.getPosCondition();
					String middle = "positiveCond";
					String right = interID;
					TupelWriter.printTriple(out, left, middle, right);
				}

			} catch (InvalidInteractionIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/*dans ce qui va suivre on va éditer un ensemble de méthode pour écrire pour chaque pattern */
	
		
	//pattern 1 pour une simple activation p{1}-p{1}
	
	    public void pattern1(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
		
	    }
	    
	    //method for write pattern 
	    public void pattern1Bis(PrintWriter out, Pattern pat){
	    	
	    	out.write("(*pattern *) \n");
	    	
	    	//les nodes view
	    	NodeView nodeView1 = null;
	    	NodeView nodeView2 = null;
	    	NodeView nodeView3 = null;
	    	NodeView nodeView4 = null;
	    	NodeView nodeView5 = null;
	    	NodeView nodeView6 = null;
	   	    
	   	    CyNetworkView view = (CyNetworkView)Cytoscape.getCurrentNetworkView(); 
	   	   
	   	    //on récupère les gènes mesurés
	   	    ListeAction lA = new ListeAction("parametresEstimes.txt");
	   	    Hashtable<String, ArrayList<ActionParameter>> genesMesures = lA.getGeneMesures();
	   	    
	   	    
	   	    
	    	int typePattern =pat.getNumberOfPattern();
	    	ArrayList<NodeOfPattern> nodePat=pat.getNodeOfPattern();
	    	
	    	switch(nodePat.size()){
	    	case 0: {out.write("(*pas de composant*) \n");
	    	         break;
	    	         }
	    	case 1: {out.write("(* composant tout seul*) \n");
	    	         CyNode node = nodePat.get(0).getCyNode();
	    	         
	    	         nodeView1 = view.getNodeView(node);
	    	         
	    	         nodeView1.getLabel().getText().replaceAll("[\\W]","_");	    	         
	    	         
	    	        break;
	    	        }   
	    	case 2: {//on a deux composants pour le pattern
   		             out.write("(*on a deux composants pour le pattern*) \n");
	                 CyNode node1 = nodePat.get(0).getCyNode();
	                 CyNode node2 = nodePat.get(1).getCyNode();
	         
	                 nodeView1=view.getNodeView(node1);
	                 nodeView2=view.getNodeView(node2);
	         
	    		     
	    		     if(genesMesures.containsKey(nodeView1.getLabel().getText())){
	    		    	 
	    		    	 out.write("(*on a un gene mesure*)");
	    		    	 //on récupère les mesures puis on écrit les actions
	    		    	 float rate;int sa;
	    		    	 int nivb,nivbp1;
	    		    	 ArrayList<ActionParameter> listeAction = genesMesures.get(nodeView1.getLabel().getText());
	    		    	 
	    		    	 for(int k=0;k<listeAction.size();k++){
	    		    		 
	    		    		 //on récupère les rates
	    		    		 rate=listeAction.get(k).getRate();
	                 		 sa=listeAction.get(k).getAbsortionStoch();
	                 		 out.write("(*Nous sommes dans un cas Param*)\n\n");
                      	     nivb=listeAction.get(k).getEtatDepart();
                      	     nivbp1=listeAction.get(k).getEtatArrive();
                      	  if(listeAction.get(k).getTypeAction().equals("activation"))
                      	  {
                      	    out.write("(*activation*)\n");
                            out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 1 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                      	  else
                      	  {
                      		  out.write("(*inibition*)\n");
                      		  out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 0 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                            
	                 		                 		 
	    		    		 
	    		    	 }
	    		    	 
	    		     }
	    		     else{//on écrit les actions par défaut
	    		     
	    	         //nodeView1.getLabel().getText().replaceAll("[\\W]","_");
	    	         //nodeView2.getLabel().getText().replaceAll("[\\W]","_");
	    	         out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 0 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" 1 0 \n");     
	    	         out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 1 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" 0 1 \n");     
	    		     }  
	    	          
	    	         break;
	    	         }
	    	case 3: {out.write("(*ici on a trois composant dans le pattern*) \n");
	    	         CyNode node1 = nodePat.get(0).getCyNode();
	                 CyNode node2 = nodePat.get(1).getCyNode();
	                 CyNode node3 = nodePat.get(2).getCyNode();
	         
	                 nodeView1=view.getNodeView(node1);
	                 nodeView2=view.getNodeView(node2);
	                 nodeView3=view.getNodeView(node3);
	                 
if(genesMesures.containsKey(nodeView1.getLabel().getText())){
	    		    	 
	    		    	 out.write("(*on a un gene mesure*)");
	    		    	 //on récupère les mesures puis on écrit les actions
	    		    	 float rate; int sa;
	    		    	 int nivb,nivbp1;
	    		    	 ArrayList<ActionParameter> listeAction = genesMesures.get(nodeView1.getLabel().getText());
	    		    	 
	    		    	 for(int k=0;k<listeAction.size();k++){
	    		    		 
	    		    		 //on récupère les rates
	    		    		 rate=listeAction.get(k).getRate();
	                 		 sa=listeAction.get(k).getAbsortionStoch();
	                 		 out.write("(*Nous sommes dans un cas Param*)\n\n");
                      	     nivb=listeAction.get(k).getEtatDepart();
                      	     nivbp1=listeAction.get(k).getEtatArrive();
                      	  if(listeAction.get(k).getTypeAction().equals("activation"))
                      	  {
                      	    out.write("(*activation*)\n");
                            out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 1 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                      	  else
                      	  {
                      		  out.write("(*inibition*)\n");
                      		  out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 0 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                            
	                 		                 		 
	    		    		 
	    		    	 }
	    		    	 
	    		     }
	    		     else{//on écrit les actions par défaut
	    		     
	                 
	    	         out.write("COOPERATIVITY([ "+nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" ; "+nodeView3.getLabel().getText().replaceAll("[\\W]","_")+" ] -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" 0 1, [[1;0]])");
	    	        
	    		     }
	    	         break;
	    	         }
	    	case 4: {out.write("(*ici on a quatre composants pour le pattern*) \n");
	               	 CyNode node1 = nodePat.get(0).getCyNode();
                     CyNode node2 = nodePat.get(1).getCyNode();
                     CyNode node3 = nodePat.get(2).getCyNode();
                     CyNode node4 = nodePat.get(3).getCyNode();
                     
                     nodeView1=view.getNodeView(node1);
                     nodeView2=view.getNodeView(node2);
                     nodeView3=view.getNodeView(node3);
                     nodeView4=view.getNodeView(node4);
	                 
if(genesMesures.containsKey(nodeView1.getLabel().getText())){
	    		    	 
	    		    	 out.write("(*on a un gene mesure*)");
	    		    	 //on récupère les mesures puis on écrit les actions
	    		    	 float rate; int sa;
	    		    	 int nivb,nivbp1;
	    		    	 ArrayList<ActionParameter> listeAction = genesMesures.get(nodeView1.getLabel().getText());
	    		    	 
	    		    	 for(int k=0;k<listeAction.size();k++){
	    		    		 
	    		    		 //on récupère les rates
	    		    		 rate=listeAction.get(k).getRate();
	                 		 sa=listeAction.get(k).getAbsortionStoch();
	                 		 out.write("(*Nous sommes dans un cas Param*)\n\n");
                      	     nivb=listeAction.get(k).getEtatDepart();
                      	     nivbp1=listeAction.get(k).getEtatArrive();
                      	  if(listeAction.get(k).getTypeAction().equals("activation"))
                      	  {
                      	    out.write("(*activation*)\n");
                            out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 1 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                      	  else
                      	  {
                      		  out.write("(*inibition*)\n");
                      		  out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 0 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                            
	                 		                 		 
	    		    		 
	    		    	 }
	    		    	 
	    		     }
	    		     else{//on écrit les actions par défaut
	    		     
                     
                     out.write("COOPERATIVITY([ "+nodeView2.getLabel().getText().replaceAll("[\\W]","_")+""
                     		+ " ; "+nodeView3.getLabel().getText().replaceAll("[\\W]","_")
                     		+ " ; "+nodeView4.getLabel().getText().replaceAll("[\\W]","_")
                     		+" ] -> "+ ""+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" 0 1, [[1;0;0]])");
	    	         
	    		     }
	    	
	    	         break;
	    	        }
	    	case 5: {out.write("(*ici on a cinq composants pour le pattern*) \n");
	    	         
    	    	     CyNode node1 = nodePat.get(0).getCyNode();
                     CyNode node2 = nodePat.get(1).getCyNode();
                     CyNode node3 = nodePat.get(2).getCyNode();
                     CyNode node4 = nodePat.get(3).getCyNode();
                     CyNode node5 = nodePat.get(4).getCyNode();
            
                     nodeView1=view.getNodeView(node1);
                     nodeView2=view.getNodeView(node2);
                     nodeView3=view.getNodeView(node3);
                     nodeView4=view.getNodeView(node4);
                     nodeView5=view.getNodeView(node5);
                     
if(genesMesures.containsKey(nodeView1.getLabel().getText())){
	    		    	 
	    		    	 out.write("(*on a un gene mesure*)");
	    		    	 //on récupère les mesures puis on écrit les actions
	    		    	 float rate; int sa;
	    		    	 int nivb,nivbp1;
	    		    	 ArrayList<ActionParameter> listeAction = genesMesures.get(nodeView1.getLabel().getText());
	    		    	 
	    		    	 for(int k=0;k<listeAction.size();k++){
	    		    		 
	    		    		 //on récupère les rates
	    		    		 rate=listeAction.get(k).getRate();
	                 		 sa=listeAction.get(k).getAbsortionStoch();
	                 		 out.write("(*Nous sommes dans un cas Param*)\n\n");
                      	     nivb=listeAction.get(k).getEtatDepart();
                      	     nivbp1=listeAction.get(k).getEtatArrive();
                      	  if(listeAction.get(k).getTypeAction().equals("activation"))
                      	  {
                      	    out.write("(*activation*)\n");
                            out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 1 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                      	  else
                      	  {
                      		  out.write("(*inibition*)\n");
                      		  out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 0 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                            
	                 		                 		 
	    		    		 
	    		    	 }
	    		    	 
	    		     }
	    		     else{//on écrit les actions par défaut
	    		     
                     
                     out.write("COOPERATIVITY([ "+nodeView2.getLabel().getText().replaceAll("[\\W]","_")+""
                      		+ " ; "+nodeView3.getLabel().getText().replaceAll("[\\W]","_")
                      		+ " ; "+nodeView4.getLabel().getText().replaceAll("[\\W]","_")
                      		+ " ; "+nodeView5.getLabel().getText().replaceAll("[\\W]","_")
                      		+" ] -> "+ ""+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" 0 1, [[1;0;0;0]])");
 	    	         
	    		     }
	    	
	    	         break;
	    	  
	    	        }
	    	case 6: {out.write("(*ici on a six composants pour le pattern*) \n");
	    	         CyNode node1 = nodePat.get(0).getCyNode();
                     CyNode node2 = nodePat.get(1).getCyNode();
                     CyNode node3 = nodePat.get(2).getCyNode();
                     CyNode node4 = nodePat.get(3).getCyNode();
                     CyNode node5 = nodePat.get(4).getCyNode();
                     CyNode node6 = nodePat.get(5).getCyNode();
                     
                     nodeView1=view.getNodeView(node1);
                     nodeView2=view.getNodeView(node2);
                     nodeView3=view.getNodeView(node3);
                     nodeView4=view.getNodeView(node4);
                     nodeView5=view.getNodeView(node5);
                     nodeView6=view.getNodeView(node6);

if(genesMesures.containsKey(nodeView1.getLabel().getText())){
	    		    	 
	    		    	 out.write("(*on a un gene mesure*)");
	    		    	 //on récupère les mesures puis on écrit les actions
	    		    	 float rate; int sa;
	    		    	 int nivb,nivbp1;
	    		    	 ArrayList<ActionParameter> listeAction = genesMesures.get(nodeView1.getLabel().getText());
	    		    	 
	    		    	 for(int k=0;k<listeAction.size();k++){
	    		    		 
	    		    		 //on récupère les rates
	    		    		 rate=listeAction.get(k).getRate();
	                 		 sa=listeAction.get(k).getAbsortionStoch();
	                 		 out.write("(*Nous sommes dans un cas Param*)\n\n");
                      	     nivb=listeAction.get(k).getEtatDepart();
                      	     nivbp1=listeAction.get(k).getEtatArrive();
                      	  if(listeAction.get(k).getTypeAction().equals("activation"))
                      	  {
                      	    out.write("(*activation*)\n");
                            out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 1 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                      	  else
                      	  {
                      		  out.write("(*inibition*)\n");
                      		  out.write(nodeView2.getLabel().getText().replaceAll("[\\W]","_")+" 0 -> "+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" "+nivb+" "+nivbp1+" @"+(10*rate)+"~"+sa+" \n\n\n");
                      	  }
                            
	                 		                 		 
	    		    		 
	    		    	 }
	    		    	 
	    		     }
	    		     else{//on écrit les actions par défaut
	    		     
                     
                     out.write("COOPERATIVITY([ "+nodeView2.getLabel().getText().replaceAll("[\\W]","_")+""
                       		+ " ; "+nodeView3.getLabel().getText().replaceAll("[\\W]","_")
                       		+ " ; "+nodeView4.getLabel().getText().replaceAll("[\\W]","_")
                       		+ " ; "+nodeView5.getLabel().getText().replaceAll("[\\W]","_")
                       		+ " ; "+nodeView6.getLabel().getText().replaceAll("[\\W]","_")
                       		+" ] -> "+ ""+nodeView1.getLabel().getText().replaceAll("[\\W]","_")+" 0 1, [[1;0;0;0;0]])");
  	    	         
                     
	    		     }
	    	         break;
	    	 
	    	         }
	    	
	    	default: out.write("(*pattern inconnue*) \n");break;
	    	}
	    	
	    }
	
	//pattern 2 pour une simple activation p{1}-TF{1}
	
		public void pattern2(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
    //pattern 3 pour une simple activation TF{1}-G{1}
		
		public void pattern3(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	//pattern 4 pour une simple inhibition p{1}-p{1}
		
		public void pattern4(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	//pattern 5 pour une simple inhibition p{1}-TF{1}
		
		public void pattern5(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
	
	//pattern 6 pour une simple inhibition TF{1}-G{1}
		
		public void pattern6(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	//pattern 7 pour une simple formation de complexe
		
		public void pattern7(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	//pattern 8 pour une simple dissociation de complexe
		
		public void pattern8(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
	
		
	//pattern 9 multiple activations plus synchronisation
				
		public void pattern9(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	//pattern 10 pour une activation/inhibition plus synchronisation 
		
		public void pattern10(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	//pattern 11 pour une catalysation 
		
		public void pattern11(PrintWriter out, MoleculeNode mn1, MoleculeNode mn2){
			
		}
		
	/**Méthodes pour déterminer les types des noeuds et d'arcs**/
		
	//Méthodes pour déterminer le type des noeuds
		public char typeNode(String typeNode){
			char type;
			if(typeNode.equals("protein"))
			type= 'p';
			else
				if(typeNode.equals("modification"))
					type = 'm';
				else 
					if(typeNode.equals("complex"))
					   type = 'c';
					else
						if(typeNode.equals("transcription"))
							type = 't';
						else
							if(typeNode.equals("biological_process"))
								type = 'b';
							else
								if(typeNode.equals("compound"))
									type = 'd';
								else
									if(typeNode.equals("translocation"))
										type = 'l';
				else
					type = 'u';
			
			return type;
			
			
		}
		
		//Méthode pour déterminer le type d'arc
		public char typeEdge(String typeEdge){
			char type;
			if(typeEdge.equals("agent"))
				type = 'a';
			else
				if(typeEdge.equals("inhibitor"))
					type = 'i';
				else
					if(typeEdge.equals("input"))
						type = 'p';
					else
						if(typeEdge.equals("output"))
							type = 'o';
						else 
						if(typeEdge.equals("isFamMemberOf"))
							type = 'f';
						else
							if(typeEdge.equals("isCompMemberOf"))
								type = 'c';
						else
							type = 'u';
			return type;
		}
		
		//méthode pour déterminer le type de incomming edge
		public boolean typeInteraction(ArrayList<CyEdge> listeInt){
			boolean type;
			boolean act=false;
			boolean out=false;
			boolean inh=false;
			boolean inp=false;
			boolean fam=false;
			for(CyEdge edge: listeInt){
				if(Cytoscape.getEdgeAttributes().getAttributeNames().equals("agent"))
					act=true;
				else
					if(Cytoscape.getEdgeAttributes().getAttributeNames().equals("inhibitor"))
						inh=true;
					else
						if(Cytoscape.getEdgeAttributes().getAttributeNames().equals("input"))
							inp=true;
						else
							if(Cytoscape.getEdgeAttributes().getAttributeNames().equals("output"))
								out=true;
							else
								if(Cytoscape.getEdgeAttributes().getAttributeNames().equals("isFamMemberOf"))
									fam=true;
									
				
				
			}
			type=(act)&&(inh)&&(inp)&&(out)&&(fam);
			
			return type;
		}
		
				
	/** Choix du pattern en fonction du type des prédecesseurs: le but de cette méthode est 
	 * de déterminer quel type de pattern sélectionner en fonction du type des prédécesseurs
	 * **/
		public Pattern choixPattern(CyNode noeudCour,CyNetwork NetTemp,Pattern Pat,PrintWriter out)
		{
			//The attributes 
			int nombrePred;
		    char typeNoeud1, typeNoeud2, typeEdge1;
		    Pattern typPattern=Pat;
			
		    //General principle
			//choix en fonction du type du noeud courant
			//choix en fonction du nombre de prédecesseur
			//choix en fonction du type de incomming edge
			//choix en fonction du type des noeuds
			
			
			System.out.println("On entre dans la procédure de détection des patterns");
			
			//on récupère une vue du graphe
			//we get the current view of the model 
			NodeView nodeView = null; 
			CyNetworkView view = (CyNetworkView)Cytoscape.getCurrentNetworkView(); 
			CyEdge arcEntrant1 = null;
			
			//on récupère le type de noeud && le nombre de pred 
			typeNoeud1=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudCour.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
			typeEdge1='u';
			typeNoeud2='u'; nombrePred=0;
			
			switch(typeNoeud1){//en focntion du type de noeud
			case 'c':{
								
				NodeOfPattern NoP=new NodeOfPattern(noeudCour,1);
				typPattern.addNode(NoP);
				//typPattern.setNumberOfPattern(1);
				nombrePred=NetTemp.getInDegree(noeudCour); 
				out.write("(*un complex2*)\n");
				switch(nombrePred){//test au niveau du nombre de Predecesseur
				
			case 0:{//ici il n'a pas de prédecesseur
				typPattern.setNumberOfPattern(11);
				out.write("((*pas de predecesseur*)\n");
				return typPattern;
			}
			
			case 1:{ //ici il a un predecesseur
				out.write("(*un  1 predecesseur*)\n");
				//on récupère son input edge
				ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
				//arcEntrant.get(0).getIdentifier();
				
				
				for(CyEdge arcEntrantTemp:arcEntrant){
				arcEntrant1=arcEntrantTemp;
				typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());
				
				}
				
				switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
				case 'a':{ //agent edge
					 
					 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           out.write("(*ici on a un predecesseur*)\n");
					           pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            out.write("(*ici on a un predecesseur*)\n");
					            pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   out.write("(*ici on a un predecesseur*)\n");
			                   pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           out.write("(*ici on a un predecesseur*)\n");
	                           pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end agent edge case
		
				case 'i':{ //inhibitor case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           out.write("(*ici on a un predecesseur*)\n");
					           pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            out.write("(*ici on a un predecesseur*)\n");
					            pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   out.write("(*ici on a un predecesseur*)\n");
			                   pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           out.write("(*ici on a un predecesseur*)\n");
	                           pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break; //end inhibitor case
					
				case 'p':{ //input case 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           out.write("(*ici on a un predecesseur*)\n");
					           pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            out.write("(*ici on a un predecesseur*)\n");
					            pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   out.write("(*ici on a un predecesseur*)\n");
			                   pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           out.write("(*ici on a un predecesseur*)\n");
	                           pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break;//end input case
					
				case 'o':{ //output case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           out.write("(*ici on a un predecesseur*)\n");
					           pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            out.write("(*ici on a un predecesseur*)\n");
					            pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   out.write("(*ici on a un predecesseur*)\n");
			                   pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           out.write("(*ici on a un predecesseur*)\n");
	                           pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 				 
				}
					break;//end output case
					
				case 'f':{ //isFamilyMemberOf
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           out.write("(*ici on a un predecesseur*)\n");
					           pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            out.write("(*ici on a un predecesseur*)\n");
					            pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   out.write("(*ici on a un predecesseur*)\n");
			                   pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           out.write("(*ici on a un predecesseur*)\n");
	                           pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end of isFamilyMemberOf
					
				case 'c':{ //isCompMemberOf 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           out.write("(*ici on a un predecesseur*)\n");
					           pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            out.write("(*ici on a un predecesseur*)\n");
					            pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   out.write("(*ici on a un predecesseur*)\n");
			                   pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           out.write("(*ici on a un predecesseur*)\n");
	                           pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break; //end of isCompMember
					
				case 'u':  //on peut générer un message d'erreur 
					break;
			}
			}
			break;
			case 2:{ //ici il a un predecesseur
				out.write("(*here we have two predecessors *)\n");
				//on récupère son input edge
				ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
				//arcEntrant.get(0).getIdentifier();
				
				
				for(CyEdge arcEntrantTemp:arcEntrant){
				arcEntrant1=arcEntrantTemp;
				typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());
				
				//here we will treated each edge
				switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
				case 'a':{ //agent edge
					 
					 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end agent edge case
		
				case 'i':{ //inhibitor case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break; //end inhibitor case
					
				case 'p':{ //input case 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break;//end input case
					
				case 'o':{ //output case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 				 
				}
					break;//end output case
					
				case 'f':{ //isFamilyMemberOf
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end of isFamilyMemberOf
					
				case 'c':{ //isCompMemberOf 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break; //end of isCompMember
					
				case 'u':  //on peut générer un message d'erreur 
					break;
			}
				
				
				
				//end of treatement of each edge
				}
		    out.write("(*here we are in the case where we have 2 predecessors *)");		
			pattern1Bis(out,typPattern);
			}
				break;
			case 3:{
				out.write("(*here we have three  predecessors *)\n");
				//on récupère son input edge
				ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
				//arcEntrant.get(0).getIdentifier();
				
				
				for(CyEdge arcEntrantTemp:arcEntrant){
				arcEntrant1=arcEntrantTemp;
				typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());
				
				//here we will treated each edge
				switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
				case 'a':{ //agent edge
					 
					 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end agent edge case
		
				case 'i':{ //inhibitor case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break; //end inhibitor case
					
				case 'p':{ //input case 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break;//end input case
					
				case 'o':{ //output case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 				 
				}
					break;//end output case
					
				case 'f':{ //isFamilyMemberOf
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end of isFamilyMemberOf
					
				case 'c':{ //isCompMemberOf 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break; //end of isCompMember
					
				case 'u':  //on peut générer un message d'erreur 
					break;
			}
				
				
				
				//end of treatement of each edge

				
				
				//end of treatment of each edge
				}
				out.write("(*here we have 3 predecessors*)");
			}
				break;
			case 4:{
				out.write("(*here we have 4  predecessors *)\n");
				//on récupère son input edge
				ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
				//arcEntrant.get(0).getIdentifier();
				
				
				for(CyEdge arcEntrantTemp:arcEntrant){
				arcEntrant1=arcEntrantTemp;
				typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());
				
				//here we will treated each edge
				switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
				case 'a':{ //agent edge
					 
					 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end agent edge case
		
				case 'i':{ //inhibitor case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break; //end inhibitor case
					
				case 'p':{ //input case 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break;//end input case
					
				case 'o':{ //output case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 				 
				}
					break;//end output case
					
				case 'f':{ //isFamilyMemberOf
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end of isFamilyMemberOf
					
				case 'c':{ //isCompMemberOf 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break; //end of isCompMember
					
				case 'u':  //on peut générer un message d'erreur 
					break;
			}
				
				
				
				//end of treatement of each edge

				
				
				//end of treatment of each edge
				}
				
				out.write("(*here we have 4 predecessors*)");
			}
				break;
			case 5:{
				out.write("(*here we have 5  predecessors *)\n");
				//on récupère son input edge
				ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
				//arcEntrant.get(0).getIdentifier();
				
				
				for(CyEdge arcEntrantTemp:arcEntrant){
				arcEntrant1=arcEntrantTemp;
				typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());
				
				//here we will treated each edge
				switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
				case 'a':{ //agent edge
					 
					 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end agent edge case
		
				case 'i':{ //inhibitor case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break; //end inhibitor case
					
				case 'p':{ //input case 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break;//end input case
					
				case 'o':{ //output case
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 				 
				}
					break;//end output case
					
				case 'f':{ //isFamilyMemberOf
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }
					 
					 				 
				}
					break;//end of isFamilyMemberOf
					
				case 'c':{ //isCompMemberOf 
					CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
					 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
					 
					 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
					 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
					 switch(typeNodeInt){
					 
					 case 'c':{typPattern.addNode(NoPAdj);
					           //out.write("(*ici on a un predecesseur*)\n");
					           //pattern1Bis(out,typPattern);
					          }break; //on peut écrire le pattern ou le retourner
					          
					 case 'p':{typPattern.addNode(NoPAdj);
					            //out.write("(*ici on a un predecesseur*)\n");
					            //pattern1Bis(out,typPattern);
					            }break; //on peut écrire le pattern ou le retourner
					            
					 case 'b':{typPattern.addNode(NoPAdj);
			                   //out.write("(*ici on a un predecesseur*)\n");
			                   //pattern1Bis(out,typPattern);
			                   }break; //on peut écrire le pattern ou le retourner
					 case 'd':{typPattern.addNode(NoPAdj);
	                           //out.write("(*ici on a un predecesseur*)\n");
	                           //pattern1Bis(out,typPattern);
	                           }break; //on peut écrire le pattern ou le retourner
	                   
					 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
			            
					 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
					 
					 case 'u':{}break; //on peut écrire un message d'erreur 
					 }	 
					 				 
				}
					break; //end of isCompMember
					
				case 'u':  //on peut générer un message d'erreur 
					break;
			}
				
			
				//end of treatment of each edge
				}
			}
				break;
				
				
			}
								
			}
				break;//end complex case
				
				
			case 'p': {
				
NodeOfPattern NoP=new NodeOfPattern(noeudCour,1);
typPattern.addNode(NoP);
//typPattern.setNumberOfPattern(1);
nombrePred=NetTemp.getInDegree(noeudCour); 
out.write("(*une proteine*)\n");
switch(nombrePred){//test au niveau du nombre de Predecesseur

case 0:{//ici il n'a pas de prédecesseur
typPattern.setNumberOfPattern(11);
out.write("((*pas de predecesseur*)\n");
return typPattern;
}

case 1:{ //ici il a un predecesseur
out.write("(*un  1 predecesseur*)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

}

switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}
}
break;
case 2:{ //ici il a un predecesseur
out.write("(*here we have two predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge
}
out.write("(*here we are in the case where we have 2 predecessors *)");		
pattern1Bis(out,typPattern);
}
break;
case 3:{
out.write("(*here we have three  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge



//end of treatment of each edge
}
out.write("(*here we have 3 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 4:{
out.write("(*here we have 4  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}

out.write("(*here we have 4 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 5:{
out.write("(*here we have 5  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
pattern1Bis(out,typPattern);
}
break;


}
				
}
				
				
				break;//end protein case
			case 'b': {
				
NodeOfPattern NoP=new NodeOfPattern(noeudCour,1);
typPattern.addNode(NoP);
//typPattern.setNumberOfPattern(1);
nombrePred=NetTemp.getInDegree(noeudCour); 
out.write("(*un bilogical process*)\n");
switch(nombrePred){//test au niveau du nombre de Predecesseur

case 0:{//ici il n'a pas de prédecesseur
typPattern.setNumberOfPattern(11);
out.write("((*pas de predecesseur*)\n");
return typPattern;
}

case 1:{ //ici il a un predecesseur
out.write("(*un  1 predecesseur*)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

}

switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}
}
break;
case 2:{ //ici il a un predecesseur
out.write("(*here we have two predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge
}
out.write("(*here we are in the case where we have 2 predecessors *)");		
pattern1Bis(out,typPattern);
}
break;
case 3:{
out.write("(*here we have three  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge



//end of treatment of each edge
}
out.write("(*here we have 3 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 4:{
out.write("(*here we have 4  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatment of each edge
}

out.write("(*here we have 4 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 5:{
out.write("(*here we have 5  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}

pattern1Bis(out,typPattern);

}
break;


}
				
}
				
				
				break;//end biological process case
			case 'd': {
				
NodeOfPattern NoP=new NodeOfPattern(noeudCour,1);
typPattern.addNode(NoP);
//typPattern.setNumberOfPattern(1);
nombrePred=NetTemp.getInDegree(noeudCour); 
out.write("(*un compound*)\n");
switch(nombrePred){//test au niveau du nombre de Predecesseur

case 0:{//ici il n'a pas de prédecesseur
typPattern.setNumberOfPattern(11);
out.write("((*pas de predecesseur*)\n");
return typPattern;
}

case 1:{ //ici il a un predecesseur
out.write("(*un  1 predecesseur*)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

}

switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}
}
break;
case 2:{ //ici il a un predecesseur
out.write("(*here we have two predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge
}
out.write("(*here we are in the case where we have 2 predecessors *)");		
pattern1Bis(out,typPattern);
}
break;
case 3:{
out.write("(*here we have three  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
out.write("(*here we have 3 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 4:{
out.write("(*here we have 4  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}

out.write("(*here we have 4 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 5:{
out.write("(*here we have 5  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
pattern1Bis(out,typPattern);
}
break;


}
				
}
				
				
				break;//end compound case
			case 'l': 
				
				
				break;//end translocation case
			case 'm': {
				
//NodeOfPattern NoP=new NodeOfPattern(noeudCour,1);
//typPattern.addNode(NoP);
//typPattern.setNumberOfPattern(1);
nombrePred=NetTemp.getInDegree(noeudCour); 
out.write("(*un compound*)\n");
switch(nombrePred){//test au niveau du nombre de Predecesseur

case 0:{//ici il n'a pas de prédecesseur
typPattern.setNumberOfPattern(11);
out.write("((*pas de predecesseur*)\n");
return typPattern;
}

case 1:{ //ici il a un predecesseur
out.write("(*un  1 predecesseur*)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

}

switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}
}
break;
case 2:{ //ici il a un predecesseur
out.write("(*here we have two predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge
}
out.write("(*here we are in the case where we have 2 predecessors *)");		
pattern1Bis(out,typPattern);
}
break;
case 3:{
out.write("(*here we have three  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
out.write("(*here we have 3 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 4:{
out.write("(*here we have 4  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}

out.write("(*here we have 4 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 5:{
out.write("(*here we have 5  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
pattern1Bis(out,typPattern);
}
break;


}
				
}

				
				
				break;//end modification case
			case 't': {
				
//NodeOfPattern NoP=new NodeOfPattern(noeudCour,1);
//typPattern.addNode(NoP);
//typPattern.setNumberOfPattern(1);
nombrePred=NetTemp.getInDegree(noeudCour); 
out.write("(*un compound*)\n");
switch(nombrePred){//test au niveau du nombre de Predecesseur

case 0:{//ici il n'a pas de prédecesseur
typPattern.setNumberOfPattern(11);
out.write("((*pas de predecesseur*)\n");
return typPattern;
}

case 1:{ //ici il a un predecesseur
out.write("(*un  1 predecesseur*)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

}

switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           out.write("(*ici on a un predecesseur*)\n");
	           pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            out.write("(*ici on a un predecesseur*)\n");
	            pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               out.write("(*ici on a un predecesseur*)\n");
               pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}
}
break;
case 2:{ //ici il a un predecesseur
out.write("(*here we have two predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}



//end of treatement of each edge
}
out.write("(*here we are in the case where we have 2 predecessors *)");		
pattern1Bis(out,typPattern);
}
break;
case 3:{
out.write("(*here we have three  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
out.write("(*here we have 3 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 4:{
out.write("(*here we have 4  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}

out.write("(*here we have 4 predecessors*)");
pattern1Bis(out,typPattern);
}
break;
case 5:{
out.write("(*here we have 5  predecessors *)\n");
//on récupère son input edge
ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)NetTemp.getAdjacentEdgesList(noeudCour, false, true, false);
//arcEntrant.get(0).getIdentifier();


for(CyEdge arcEntrantTemp:arcEntrant){
arcEntrant1=arcEntrantTemp;
typeEdge1=typeEdge(Cytoscape.getEdgeAttributes().getAttribute(arcEntrant1.getIdentifier(), Cytoscape.getEdgeAttributes().getAttributeNames()[1]).toString());

//here we will treated each edge
switch(typeEdge1){ //test au niveau du type des arcs ou des interactions ce qui donne un nouveau numéro au pattern
case 'a':{ //agent edge
	 
	 CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end agent edge case

case 'i':{ //inhibitor case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break; //end inhibitor case
	
case 'p':{ //input case 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break;//end input case
	
case 'o':{ //output case
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 				 
}
	break;//end output case
	
case 'f':{ //isFamilyMemberOf
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }
	 
	 				 
}
	break;//end of isFamilyMemberOf
	
case 'c':{ //isCompMemberOf 
	CyNode noeudAdj=(CyNode)arcEntrant.get(0).getSource();
	 NodeOfPattern NoPAdj=new NodeOfPattern(noeudAdj,111);
	 
	 //il faut vérifier la condition d'arrêt: si c'est un noeud terminal 
	 char typeNodeInt=typeNode(Cytoscape.getNodeAttributes().getAttribute(noeudAdj.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
	 switch(typeNodeInt){
	 
	 case 'c':{typPattern.addNode(NoPAdj);
	           //out.write("(*ici on a un predecesseur*)\n");
	           //pattern1Bis(out,typPattern);
	          }break; //on peut écrire le pattern ou le retourner
	          
	 case 'p':{typPattern.addNode(NoPAdj);
	            //out.write("(*ici on a un predecesseur*)\n");
	            //pattern1Bis(out,typPattern);
	            }break; //on peut écrire le pattern ou le retourner
	            
	 case 'b':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
	 case 'd':{typPattern.addNode(NoPAdj);
               //out.write("(*ici on a un predecesseur*)\n");
               //pattern1Bis(out,typPattern);
               }break; //on peut écrire le pattern ou le retourner
       
	 case 'l':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
        
	 case 'm':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 't':{choixPattern(noeudAdj,NetTemp,typPattern,out);}break; //on refait un appel récursif
	 
	 case 'u':{}break; //on peut écrire un message d'erreur 
	 }	 
	 				 
}
	break; //end of isCompMember
	
case 'u':  //on peut générer un message d'erreur 
	break;
}


//end of treatment of each edge
}
pattern1Bis(out,typPattern);
}
break;


}
				
}

				break;//end transcription case
			case 'u': 
				break;//end unknow case
				default:
					break;
			
			}
			return typPattern;
			
		}
		
		
		
		
	/**methode pour générer le code en PH à partir d'un modèle(graphe)
	 * L'idée de la méthode est de parcourir le graphe et d'identifier les patterns 
	 * du model **/
	
   public void writePH(PrintWriter out, Map<MoleculeNode, ArrayList<InteractionNode>> graph)
   {
	   
	   System.out.println("Hey!");
	   
	   //etat initial
	   int etat=1; //état initial de l'automate
	   int nombrePred;
	   char mesure;
	   
	   
	   //on va parcourir les sommets du graphes 
	   for(MoleculeNode molc:graph.keySet()){
		   
		   //on indique dans quel état on se trouve 
		   System.out.println("Etat___"+etat+"sommet__"+molc.getPid());
		   
		   if(geneMesures.contains(molc))
			   mesure='o';
		   else
			   mesure='n';
		   
		   switch(mesure){
		   
		   //dans le cas où on a la mesure
		   
		      case 'o':System.out.println("on  a la mesure");
		               break;
			   
		   //dans le cas où on a pas la mesure 
		      
		      case 'n': System.out.println("on a pas la mesure");
		      
		      //on récupère le nombre de predecesseur 
		      nombrePred=graph.get(molc).size();
		      
		      // en fonction de ce nombre on choisi dans quel sous cas on se trouve
		      switch(nombrePred){
		      
		      //on a pas de prédecesseur
		      case 0 : System.out.println("pas de predecesseur");
		      
		      //on a un predecesseur
		      case 1 : System.out.println("1 predecesseur");
		      
		      // on a deux predecesseurs
		      case 2 : System.out.println("2 predecesseur");
		      
		      //on a trois predecesseurs 
		      case 3 : System.out.println("3 predecesseur");
		      
		      // on a plus de trois prédecesseurs et c'est pas encore pris en compte
		      default: System.out.println("nombre trop important pas encore pris en compte");
		      }
		    	 
			
		   
		   }
		   
		   
	   }
	   
   }
   
   /**Méthode pour générer un modèle PH à partir d'un graphe d'interaction de Cytoscape
    * elle prend en entrée le fichier de sortie et le graphe de Cytoscape**/
	
  @SuppressWarnings("deprecation")
public void writePHfromCyto(PrintWriter out,CyNetwork Net)
  {
	  System.out.println("ici on parcour le graph sous cytosacpe pour détecter les interactions");
	  
	  //l'idée est de programmer un automate qui va faire ce travail de façon automatique
	  
	  int etat=1;  //etat initial de l'automate
	  int nombrePred; //le nombre de predecesseur d'un noeud
	  char mesure;  //valeur qui détermine si on a la mesure sur un noeud donné
	  //CyNode noeudCourant; //noeud courant pendant le parcour du graphe par l'automate
	  CyAttributes attrib = Cytoscape.getOntologyAttributes(); attrib.getAttributeNames();
	 	  
	  
	  for(int i=0;i<Cytoscape.getNodeAttributes().getAttributeNames().length;i++){
		  
	  //System.out.println("les edges attributes: "+i+" "+Cytoscape.getEdgeAttributes().getAttributeNames()[i]);
	  System.out.println("les nodes attributes: "+i+" "+Cytoscape.getNodeAttributes().getAttributeNames()[i]);
	 	  
	  }
	  
	  // case for Node table
	  Set<CyNode> nodesAttribute = new HashSet<CyNode>();
	  	  
	  Net.nodesList().size();   //au cas où il faut revenir à cytoscape de base
	 	  
	  System.out.println("taille du graphe"+Net.nodesList().size()); // on affiche le nombre de noeud du graphe
	  ArrayList<CyNode> allNodes = (ArrayList<CyNode>) Net.nodesList(); // on récupère les noeuds du graphe 
	  
	 
	  //we get the current view of the model 
	  NodeView nodeView = null; 
	  CyNetworkView view = (CyNetworkView)Cytoscape.getCurrentNetworkView(); 
	  
	  
	  //we create the output file for the ph generation code
	  
	 try{
		 	 
	        
	      //initialisation of PH file output
		  out.write("(* directives *) \n\n");
		  out.write("directive default_rate 10.0 \n\n");
		  out.write("directive stochasticity_absorption 50 \n\n");
	        
	      out.write("(* The Sorts & Process of our Network*) \n \n");
	        
	      for(CyNode noeudCourantTemp : allNodes){
	    	  
	    	  nodeView = view.getNodeView(noeudCourantTemp);
			  view.getAllNodePropertyData(noeudCourantTemp.getRootGraphIndex()); 
			  System.out.println("les labels :"+nodeView.getLabel().getText());
	    	  if(nodeView.getLabel().getText().equals("MKP3{ }")||nodeView.getLabel().getText().equals("MKP1")||nodeView.getLabel().getText().equals("uPAR")||nodeView.getLabel().getText().equals("HES5")||nodeView.getLabel().getText().equals("IL1 beta")||
	    			  nodeView.getLabel().getText().equals("A20")||nodeView.getLabel().getText().equals("SM22")||nodeView.getLabel().getText().equals("IL8")||nodeView.getLabel().getText().equals("IL8[c]")||nodeView.getLabel().getText().equals("IL8[er]")||nodeView.getLabel().getText().equals("ET1")||
	    			  nodeView.getLabel().getText().equals("TNF-a")||nodeView.getLabel().getText().equals("TfR")||nodeView.getLabel().getText().equals("DKK1")){
	    		  
	    		  this.geneMesuresCy.add(noeudCourantTemp);
	    		  out.write("process "+nodeView.getLabel().getText().replaceAll("[\\W]","_")+" 2 \n");
	    			    		  
	    		  }
	    	  else 
	    		  if(nodeView.getLabel().getText().equals(""))
	    			  continue;
	    		  else
	    		  out.write("process "+nodeView.getLabel().getText().replaceAll("[\\W]","_")+" 1 \n");
	    	      }
	      //fin de l'écriture des sortes
	      out.write("\n \n");
	      
	      
	      
	  //we start write the interactions
	  out.write("(*The interactions....*) \n \n");
	  
	  //on parcours le graphe à la recherche des motifs
	
	  //on détecte les motifs
	
	 //on écrit les motifs
	   
	  for(CyNode noeudCourant: allNodes)
	  {
		 		 
		 //on indique dans quel état on se trouve 
		   System.out.println("Etat___"+etat+"__sommet__"+noeudCourant.getIdentifier());
		  // System.out.println("Etat___"+etat+"__sommet__"+noeudCourant.getSUID());
		   System.out.println("les noms attributs : "+Cytoscape.getNodeAttributes().getAttributeNames()+" le type des noeuds: "+Cytoscape.getNetworkAttributes().getType(noeudCourant.getIdentifier()));
		   System.out.println("les descriptions des attributs : "+Cytoscape.getCurrentNetworkView()+" le type des noeuds: "+Cytoscape.getNetworkView(noeudCourant.getIdentifier()).getView());
           
		   // List list = Cytoscape.getNodeAttributes().getListAttribute(noeudCourant.getIdentifier(),"NODE_TYPE");
		   
		   nodeView = (NodeView)view.getNodeView(noeudCourant);
		  // view.getAllNodePropertyData(noeudCourant.getRootGraphIndex()); 
		   System.out.println("les labels :"+nodeView.getLabel().getText().replace(" ", "_"));
		   System.out.println("les labels :"+view.getAllNodePropertyData(0));
		   System.out.println("les noeuds : "+nodeView.getNode());
		   
		   //on affiche les attributs de tous les noeuds du graphes
		   System.out.println("les attributs des noeuds courants: "+Cytoscape.getNodeAttributes().getAttribute(noeudCourant.getIdentifier(), Cytoscape.getNodeAttributes().getAttributeNames()[7]).toString());
		   
		   //for each node of the network we print is incomming edge attribute
		   ArrayList<CyEdge> arcEntrant=(ArrayList<CyEdge>)Net.getAdjacentEdgesList(noeudCourant, false, true, false);
		   for(CyEdge arc:arcEntrant){
			   
			   for(int i=0;i<Cytoscape.getEdgeAttributes().getAttributeNames().length;i++){
			   System.out.println("les attributs des arcs pour le champ "+i+"  "+Cytoscape.getEdgeAttributes().getAttribute(arc.getIdentifier(),Cytoscape.getEdgeAttributes().getAttributeNames()[i]));
			   }
		   }
		   
		   		   
		   System.out.println("Etat___"+etat+"__sommet__Indegree:"+Net.getInDegree(noeudCourant));
		   Pattern patternTemp=new Pattern();
		   System.out.println("test");
	       choixPattern(noeudCourant,Net,patternTemp,out);
		   
		   	  
		  
	  }
	  
	//here we write the initial state
	  out.write("(*The initial state....*) \n \n");
	  out.write("initial_state \n");
	  
	  for(CyNode noeudCourantTemp : allNodes){
    	  
    	  nodeView =(NodeView) view.getNodeView(noeudCourantTemp); 
		  //view.getAllNodePropertyData(noeudCourantTemp.getRootGraphIndex());   à restaurer...
		  System.out.println("les labels pour les états initiaux:"+nodeView.getLabel().getText());
    	  if(nodeView.getLabel().getText().contains("E-cadherin[i]")){
    		  
    		  out.write(nodeView.getLabel().getText().replaceAll("[\\W]","_")+" 1, ");
    		  
    		  }
    	  else
    		  if(nodeView.getLabel().getText().equals(""))
    			  continue;
    		  else
    		  out.write(nodeView.getLabel().getText().replaceAll("[\\W]","_")+" 0, ");
    	  
    	  
      }
	  
	
	out.close(); //closing the file
	  
  
  }
	catch(Exception e)
	{
		e.getMessage();
	}
	
	
	
	//GENERATE PH CODE - END
  }
}
	
