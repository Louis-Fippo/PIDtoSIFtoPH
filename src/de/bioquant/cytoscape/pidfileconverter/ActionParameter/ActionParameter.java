package de.bioquant.cytoscape.pidfileconverter.ActionParameter;


public class ActionParameter {
	
	//attributs
	
		//les etats
		private int etatDepart;
		private int etatArrive;
		//le type
		private String typeAction;
		//les paramètres(r et sa)
		private float rate;
		private int absortionStoch;
		
		//constructeur
		ActionParameter(int etatD,int etatA, String typeAc, float r, int sa)
		{
			this.setEtatDepart(etatD);
			this.setEtatArrive(etatA);
			this.setTypeAction(typeAc);
			this.setRate(r);
			this.setAbsortionStoch(sa);
		}

		public float getRate() {
			return rate;
		}

		public void setRate(float rate) {
			this.rate = rate;
		}

		public int getAbsortionStoch() {
			return absortionStoch;
		}

		public void setAbsortionStoch(int absortionStoch) {
			this.absortionStoch = absortionStoch;
		}

		public int getEtatDepart() {
			return etatDepart;
		}

		public void setEtatDepart(int etatDepart) {
			this.etatDepart = etatDepart;
		}

		public int getEtatArrive() {
			return etatArrive;
		}

		public void setEtatArrive(int etatArrive) {
			this.etatArrive = etatArrive;
		}

		public String getTypeAction() {
			return typeAction;
		}

		public void setTypeAction(String typeAction) {
			this.typeAction = typeAction;
		}
		
	

}
