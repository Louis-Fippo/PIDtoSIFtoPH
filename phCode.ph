(* directives *) 

directive default_rate 10.0 

directive stochasticity_absorption 50 

(* The Sorts & Process of our Network*) 
 
process JUN_JUND____n_ 1 
process JUN_FOS 1 
process LEF1_beta_catenin____n_ 1 
process RSK2___ 1 
process TNF_alpha_er_ 1 
process ATF2____n_ 1 
process RAC1_CDC42_GTP___ 1 
process cell_cycle_arrest 1 
process JUN_FOS____n_ 1 
process sTNF_alpha_TNFR1A_MADD____itm_ 1 
process uPAR__dimer__pm_ 1 
process PTEN 1 
process uPA_uPAR__dimer_____pm_ 1 
process AKT1___ 1 
process p38_alpha___ 1 
process Fos_n_ 1 
process GSK3beta____c_ 1 
process GAB1____pm_ 1 
process PDK1____pm_ 1 
process NF_kappa_B1_p50_RelA____n_ 1 
process DKK1 2 
process TfR 2 
process JNK_cascade 1 
process alpha_catenin_c_ 1 
process E_cadherin_c_ 1 
process ATF2 1 
process PI_3_4_5_P3_pm_ 1 
process ATF2_JUND_macroH2A_n_ 1 
process MEKK1___ 1 
process ET1 2 
process Hes5_n_ 1 
process EGFR__dimer_____itm_ 1 
process MKP1____n_ 1 
process IKK_complex_c_ 1 
process E_cadherin_lpm_ 1 
process PI_4_5_P2_pm_ 1 
process STAT3__dimer_____n_ 1 
process NF_kappa_B1_p50_RelA____c_ 1 
process ATF2_JUND____n_ 1 
process Rho_family_GTPase 1 
process JUN_FOS____n_ 1 
process beta_catenin_c_ 1 
process P110_alpha 1 
process Rho_Family_GTPase_active___ 1 
process CEBPA_BRM_RB1_E2F4_n_ 1 
process TNFR1A_BAG4 1 
process Src___ 1 
process RhoA_GTP___ 1 
process Erk1_2_active___ 1 
process Erk1_2_active____n_ 1 
process keratinocyte_differentiation 1 
process PIP5K1C____cj_ 1 
process PI3K_Class_IA_family___ 1 
process PI_3_4_5_P3_cj_ 1 
process NF_kappa_B1_p50_RelA_I_kappa_B_alpha_n_ 1 
process MKK4_MKK7___ 1 
process MAPKKK_cascade 1 
process IL1_beta 2 
process MKP3___ 2 
process Jun____n_ 1 
process cell_adhesion 1 
process ELK1_SRF____n_ 1 
process RB1 1 
process RSK2 1 
process STAT1_3__dimer_ 1 
process MKP1 2 
process STAT3____c_ 1 
process PI_4_5_P2_cj_ 1 
process NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 1 
process IL8 2 
process PIP3_pm_ 1 
process E_cadherin_itm_ 1 
process IKK_alpha_c_ 1 
process RAC1_GTP____pm_ 1 
process E_cadherin_i_ 1 
process PI3K 1 
process RALA_GTP____pm_ 1 
process PDK1___ 1 
process SM22 2 
process beta_catenin_n_ 1 
process Rac1b_GTP___ 1 
process AKT1____pm_ 1 
process BAG4 1 
process Jnk1___ 1 
process PI3K____pm_ 1 
process MKK4____c_ 1 
process CDC42_GTP____pm_ 1 
process MYC_Max_MIZ_1_n_ 1 
process RhoA_GDP___ 1 
process MKK4___ 1 
process Jun___ 1 
process JUN_FOS_n_ 1 
process E_cadherin_beta_catenin_alpha_catenin_bpm_ 1 
process FOS 1 
process MYC 1 
process AKT1____pm_ 1 
process NF_kappa_B1_p50_RelA____c_ 1 
process TNFR1A_BAG4_itm_ 1 
process MKK4____c_ 1 
process ATF2_JUN____n_ 1 
process ELK1_n_ 1 
process E_cadherin 1 
process MYC_Max_n_ 1 
process STAT1_3__dimer____ 1 
process RAC1_GTP____cj_ 1 
process Rac1b_GDP___ 1 
process RALGDS____pm_ 1 
process PI3K_Class_IA_family____pm_ 1 
process MYC_Max_n_ 1 
process HRAS_GTP____pm_ 1 
process Axin1____c_ 1 
process IKK_beta___ 1 
process nPKC_delta___ 1 
process STAT3__dimer____ 1 
process JNK_family_active___ 1 
process IL8_c_ 2 
process TNFR1A_BAG4_TNF_alpha 1 
process c_Jun_n_ 1 
process E_cadherin_beta_catenin_alpha_catenin_cj_ 1 
process A20 2 
process mTOR___ 1 
process sTNF_alpha_TNFR1A____itm_ 1 
process E_cadherin_Ca2__beta_catenin_alpha_catenin_cj_ 1 
process NF_kappa_B1_p50_RelA____n_ 1 
process uPAR 2 
process JUN_JUN_FOS____n_ 1 
process AKT1_2_active___ 1 
process IL8_er_ 2 
process PI3K____cj_ 1 

 
(*The interactions....*) 
 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Jun___ 0 -> JUN_JUND____n_ 1 0 
Jun___ 1 -> JUN_JUND____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Jun___ ; Jun___ ] -> JUN_JUND____n_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Jun___ ; Jun___ ] -> JUN_JUND____n_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ] -> JUN_FOS 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 0 -> NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 1 0 
NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 1 -> NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
beta_catenin_n_ 0 -> LEF1_beta_catenin____n_ 1 0 
beta_catenin_n_ 1 -> LEF1_beta_catenin____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RSK2 0 -> RSK2___ 1 0 
RSK2 1 -> RSK2___ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
STAT1_3__dimer____ 0 -> TNF_alpha_er_ 1 0 
STAT1_3__dimer____ 1 -> TNF_alpha_er_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Jnk1___ 0 -> ATF2____n_ 1 0 
Jnk1___ 1 -> ATF2____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Jnk1___ ; Jnk1___ ] -> ATF2____n_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Jnk1___ ; Jnk1___ ] -> ATF2____n_ 0 1, [[1;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K____pm_ 0 -> RAC1_CDC42_GTP___ 1 0 
PI3K____pm_ 1 -> RAC1_CDC42_GTP___ 0 1 
(*un bilogical process*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ STAT3__dimer_____n_ ; STAT3__dimer_____n_ ] -> cell_cycle_arrest 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Jun___ 0 -> JUN_FOS____n_ 1 0 
Jun___ 1 -> JUN_FOS____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
ATF2 0 -> ATF2 1 0 
ATF2 1 -> ATF2 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
sTNF_alpha_TNFR1A____itm_ 0 -> sTNF_alpha_TNFR1A_MADD____itm_ 1 0 
sTNF_alpha_TNFR1A____itm_ 1 -> sTNF_alpha_TNFR1A_MADD____itm_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
uPAR 0 -> uPAR__dimer__pm_ 1 0 
uPAR 1 -> uPAR__dimer__pm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
JUN_JUN_FOS____n_ 0 -> PTEN 1 0 
JUN_JUN_FOS____n_ 1 -> PTEN 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
uPAR__dimer__pm_ 0 -> uPA_uPAR__dimer_____pm_ 1 0 
uPAR__dimer__pm_ 1 -> uPA_uPAR__dimer_____pm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
AKT1____pm_ 0 -> AKT1___ 1 0 
AKT1____pm_ 1 -> AKT1___ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MKK4____c_ 0 -> p38_alpha___ 1 0 
MKK4____c_ 1 -> p38_alpha___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active____n_ 0 -> Fos_n_ 1 0 
Erk1_2_active____n_ 1 -> Fos_n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Erk1_2_active____n_ ; Erk1_2_active____n_ ] -> Fos_n_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Erk1_2_active____n_ ; Erk1_2_active____n_ ] -> Fos_n_ 0 1, [[1;0]])(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
AKT1____pm_ 0 -> GSK3beta____c_ 1 0 
AKT1____pm_ 1 -> GSK3beta____c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PIP3_pm_ 0 -> PDK1____pm_ 1 0 
PIP3_pm_ 1 -> PDK1____pm_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
MYC_Max_n_ 1 -> DKK1 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> DKK1 2 1 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*activation*)
MYC_Max_n_ 1 -> DKK1 1 2 @2.5~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> DKK1 2 1 @1.25~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> DKK1 1 0 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> DKK1 1 0 @0.5555556~50 


(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> TfR 2 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> TfR 1 0 @5.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
MYC_Max_n_ 1 -> TfR 0 1 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
MYC_Max_n_ 0 -> TfR 1 0 @2.5~50 


(*Nous sommes dans un cas Param*)

(*activation*)
MYC_Max_n_ 1 -> TfR 0 1 @1.25~50 


(*Nous sommes dans un cas Param*)

(*activation*)
MYC_Max_n_ 1 -> TfR 0 1 @1.25~50 


(*une proteine*)
(*here we have 4  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Src___ 0 -> alpha_catenin_c_ 1 0 
Src___ 1 -> alpha_catenin_c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Src___ ; Src___ ] -> alpha_catenin_c_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ Src___ ; Src___ ; Src___ ] -> alpha_catenin_c_ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ Src___ ; Src___ ; Src___ ; Src___ ] -> alpha_catenin_c_ 0 1, [[1;0;0;0]])(*here we have 4 predecessors*)(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ Src___ ; Src___ ; Src___ ; Src___ ] -> alpha_catenin_c_ 0 1, [[1;0;0;0]])(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K____pm_ 0 -> E_cadherin_c_ 1 0 
PI3K____pm_ 1 -> E_cadherin_c_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active___ 0 -> ATF2 1 0 
Erk1_2_active___ 1 -> ATF2 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have 4  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K____pm_ 0 -> PI_3_4_5_P3_pm_ 1 0 
PI3K____pm_ 1 -> PI_3_4_5_P3_pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI3K____pm_ ; PI3K____pm_ ] -> PI_3_4_5_P3_pm_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI3K____pm_ ; PI3K____pm_ ; PI3K____pm_ ] -> PI_3_4_5_P3_pm_ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ PI3K____pm_ ; PI3K____pm_ ; PI3K____pm_ ; PI3K____pm_ ] -> PI_3_4_5_P3_pm_ 0 1, [[1;0;0;0]])(*here we have 4 predecessors*)(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ PI3K____pm_ ; PI3K____pm_ ; PI3K____pm_ ; PI3K____pm_ ] -> PI_3_4_5_P3_pm_ 0 1, [[1;0;0;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
ATF2_JUND____n_ 0 -> ATF2_JUND_macroH2A_n_ 1 0 
ATF2_JUND____n_ 1 -> ATF2_JUND_macroH2A_n_ 0 1 
(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RAC1_GTP____pm_ 0 -> MEKK1___ 1 0 
RAC1_GTP____pm_ 1 -> MEKK1___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ RAC1_GTP____pm_ ; RAC1_GTP____pm_ ] -> MEKK1___ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ RAC1_GTP____pm_ ; RAC1_GTP____pm_ ] -> MEKK1___ 0 1, [[1;0]])(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS 1 -> ET1 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS 1 -> ET1 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS 0 -> ET1 2 1 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS 0 -> ET1 2 1 @0.5555556~50 


(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
mTOR___ 0 -> Hes5_n_ 1 0 
mTOR___ 1 -> Hes5_n_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
uPA_uPAR__dimer_____pm_ 0 -> EGFR__dimer_____itm_ 1 0 
uPA_uPAR__dimer_____pm_ 1 -> EGFR__dimer_____itm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active___ 0 -> MKP1____n_ 1 0 
Erk1_2_active___ 1 -> MKP1____n_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
IKK_alpha_c_ 0 -> IKK_complex_c_ 1 0 
IKK_alpha_c_ 1 -> IKK_complex_c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PTEN 0 -> PTEN 1 0 
PTEN 1 -> PTEN 0 1 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GDP___ 0 -> Rac1b_GDP___ 1 0 
Rac1b_GDP___ 1 -> Rac1b_GDP___ 0 1 
(*une proteine*)
(*pas de predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_3_4_5_P3_pm_ 0 -> PI_4_5_P2_pm_ 1 0 
PI_3_4_5_P3_pm_ 1 -> PI_4_5_P2_pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> PI_4_5_P2_pm_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> PI_4_5_P2_pm_ 0 1, [[1;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active___ 0 -> NF_kappa_B1_p50_RelA____c_ 1 0 
Erk1_2_active___ 1 -> NF_kappa_B1_p50_RelA____c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Erk1_2_active___ ; Erk1_2_active___ ] -> NF_kappa_B1_p50_RelA____c_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Erk1_2_active___ ; Erk1_2_active___ ] -> NF_kappa_B1_p50_RelA____c_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
ATF2____n_ 0 -> ATF2_JUND____n_ 1 0 
ATF2____n_ 1 -> ATF2_JUND____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
IKK_complex_c_ 0 -> IKK_complex_c_ 1 0 
IKK_complex_c_ 1 -> IKK_complex_c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ] -> Rho_family_GTPase 0 1, [[1;0]])(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Axin1____c_ 0 -> beta_catenin_c_ 1 0 
Axin1____c_ 1 -> beta_catenin_c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Axin1____c_ ; Axin1____c_ ] -> beta_catenin_c_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Axin1____c_ ; Axin1____c_ ] -> beta_catenin_c_ 0 1, [[1;0]])(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ ; NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ ] -> P110_alpha 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rho_family_GTPase 0 -> Rho_Family_GTPase_active___ 1 0 
Rho_family_GTPase 1 -> Rho_Family_GTPase_active___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RB1 0 -> CEBPA_BRM_RB1_E2F4_n_ 1 0 
RB1 1 -> CEBPA_BRM_RB1_E2F4_n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
BAG4 0 -> TNFR1A_BAG4 1 0 
BAG4 1 -> TNFR1A_BAG4 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RALA_GTP____pm_ 0 -> Src___ 1 0 
RALA_GTP____pm_ 1 -> Src___ 0 1 
(*un complex2*)
(*here we have three  predecessors *)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ RAC1_GTP____pm_ ; RAC1_GTP____pm_ ] -> RhoA_GTP___ 0 1, [[1;0]])(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ RAC1_GTP____pm_ ; RAC1_GTP____pm_ ; RAC1_GTP____pm_ ; RAC1_GTP____pm_ ] -> RhoA_GTP___ 0 1, [[1;0;0;0]])(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*pattern inconnue*) 
(*here we have 3 predecessors*)(*une proteine*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MAPKKK_cascade 0 -> Erk1_2_active___ 1 0 
MAPKKK_cascade 1 -> Erk1_2_active___ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RAC1_GTP____cj_ 0 -> PIP5K1C____cj_ 1 0 
RAC1_GTP____cj_ 1 -> PIP5K1C____cj_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K 0 -> PI3K_Class_IA_family___ 1 0 
PI3K 1 -> PI3K_Class_IA_family___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI3K____cj_ ; PI3K____cj_ ] -> PI_3_4_5_P3_cj_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
NF_kappa_B1_p50_RelA____n_ 0 -> NF_kappa_B1_p50_RelA_I_kappa_B_alpha_n_ 1 0 
NF_kappa_B1_p50_RelA____n_ 1 -> NF_kappa_B1_p50_RelA_I_kappa_B_alpha_n_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GTP___ 0 -> MKK4_MKK7___ 1 0 
Rac1b_GTP___ 1 -> MKK4_MKK7___ 0 1 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
EGFR__dimer_____itm_ 0 -> EGFR__dimer_____itm_ 1 0 
EGFR__dimer_____itm_ 1 -> EGFR__dimer_____itm_ 0 1 
(*un bilogical process*)
(*here we have three  predecessors *)
(*here we have 3 predecessors*)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ HRAS_GTP____pm_ ; HRAS_GTP____pm_ ; HRAS_GTP____pm_ ] -> MAPKKK_cascade 0 1, [[1;0;0]])(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
ATF2____n_ 0 -> ATF2____n_ 1 0 
ATF2____n_ 1 -> ATF2____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K____cj_ 0 -> PI3K____cj_ 1 0 
PI3K____cj_ 1 -> PI3K____cj_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
STAT1_3__dimer____ 1 -> IL1_beta 0 1 @5.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
STAT1_3__dimer____ 1 -> IL1_beta 1 2 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
STAT1_3__dimer____ 0 -> IL1_beta 2 1 @0.8333334~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
STAT1_3__dimer____ 0 -> IL1_beta 1 0 @0.4166667~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
STAT1_3__dimer____ 0 -> IL1_beta 1 0 @0.4166667~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*inibition*)
Erk1_2_active___ 0 -> MKP3___ 1 0 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> MKP3___ 0 1 @5.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> MKP3___ 1 2 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
Erk1_2_active___ 0 -> MKP3___ 2 1 @1.6666667~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> MKP3___ 1 2 @1.25~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> MKP3___ 1 2 @1.25~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have 5  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
c_Jun_n_ 0 -> Jun____n_ 1 0 
c_Jun_n_ 1 -> Jun____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ] -> Jun____n_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ] -> Jun____n_ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ] -> Jun____n_ 0 1, [[1;0;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a six composants pour le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ] -> Jun____n_ 0 1, [[1;0;0;0;0]])(*pattern *) 
(*ici on a six composants pour le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ; c_Jun_n_ ] -> Jun____n_ 0 1, [[1;0;0;0;0]])(*un bilogical process*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GTP___ ; Rac1b_GTP___ ] -> cell_adhesion 0 1, [[1;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
ELK1_n_ 0 -> ELK1_SRF____n_ 1 0 
ELK1_n_ 1 -> ELK1_SRF____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
ATF2____n_ 0 -> RB1 1 0 
ATF2____n_ 1 -> RB1 0 1 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active____n_ 0 -> Erk1_2_active____n_ 1 0 
Erk1_2_active____n_ 1 -> Erk1_2_active____n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active___ 0 -> RSK2 1 0 
Erk1_2_active___ 1 -> RSK2 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
TNFR1A_BAG4 0 -> TNFR1A_BAG4 1 0 
TNFR1A_BAG4 1 -> TNFR1A_BAG4 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
STAT3__dimer____ 0 -> STAT1_3__dimer_ 1 0 
STAT3__dimer____ 1 -> STAT1_3__dimer_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
ATF2_JUN____n_ 1 -> MKP1 0 1 @5.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
ATF2_JUN____n_ 1 -> MKP1 1 2 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
ATF2_JUN____n_ 0 -> MKP1 2 1 @2.5~50 


(*Nous sommes dans un cas Param*)

(*activation*)
ATF2_JUN____n_ 1 -> MKP1 1 2 @2.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
ATF2_JUN____n_ 0 -> MKP1 2 1 @1.6666667~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
ATF2_JUN____n_ 0 -> MKP1 2 1 @1.6666667~50 


(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
nPKC_delta___ 0 -> STAT3____c_ 1 0 
nPKC_delta___ 1 -> STAT3____c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PIP5K1C____cj_ 0 -> PI_4_5_P2_cj_ 1 0 
PIP5K1C____cj_ 1 -> PI_4_5_P2_cj_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 2 1 @0.8333334~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 1 0 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 1 0 @0.5555556~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 2 1 @0.8333334~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 1 0 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 1 0 @0.5555556~50 


(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 2 1 @0.8333334~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 1 0 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8 1 0 @0.5555556~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PDK1____pm_ 0 -> PDK1____pm_ 1 0 
PDK1____pm_ 1 -> PDK1____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K____pm_ 0 -> PI3K____pm_ 1 0 
PI3K____pm_ 1 -> PI3K____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_4_5_P2_pm_ 0 -> PIP3_pm_ 1 0 
PI_4_5_P2_pm_ 1 -> PIP3_pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_4_5_P2_pm_ ; PI_4_5_P2_pm_ ] -> PIP3_pm_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_4_5_P2_pm_ ; PI_4_5_P2_pm_ ] -> PIP3_pm_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Src___ 0 -> E_cadherin_itm_ 1 0 
Src___ 1 -> E_cadherin_itm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
AKT1____pm_ 0 -> IKK_alpha_c_ 1 0 
AKT1____pm_ 1 -> IKK_alpha_c_ 0 1 
(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GDP___ 0 -> RAC1_GTP____pm_ 1 0 
Rac1b_GDP___ 1 -> RAC1_GTP____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ] -> RAC1_GTP____pm_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ] -> RAC1_GTP____pm_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
P110_alpha 0 -> PI3K 1 0 
P110_alpha 1 -> PI3K 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RALGDS____pm_ 0 -> RALA_GTP____pm_ 1 0 
RALGDS____pm_ 1 -> RALA_GTP____pm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
RSK2___ 0 -> PDK1___ 1 0 
RSK2___ 1 -> PDK1___ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
ELK1_SRF____n_ 1 -> SM22 0 1 @2.5~50 


(*Nous sommes dans un cas Param*)

(*activation*)
ELK1_SRF____n_ 1 -> SM22 1 2 @2.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
ELK1_SRF____n_ 0 -> SM22 2 1 @1.6666667~50 


(*Nous sommes dans un cas Param*)

(*activation*)
ELK1_SRF____n_ 1 -> SM22 1 2 @0.8333334~50 


(*Nous sommes dans un cas Param*)

(*activation*)
ELK1_SRF____n_ 1 -> SM22 1 2 @0.8333334~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K 0 -> PI3K 1 0 
PI3K 1 -> PI3K 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K____pm_ 0 -> PI3K____pm_ 1 0 
PI3K____pm_ 1 -> PI3K____pm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GDP___ 0 -> Rac1b_GDP___ 1 0 
Rac1b_GDP___ 1 -> Rac1b_GDP___ 0 1 
(*un complex2*)
(*here we have 5  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GDP___ 0 -> Rac1b_GTP___ 1 0 
Rac1b_GDP___ 1 -> Rac1b_GTP___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ] -> Rac1b_GTP___ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ; Rac1b_GDP___ ] -> Rac1b_GTP___ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ; Rac1b_GDP___ ; Rac1b_GDP___ ] -> Rac1b_GTP___ 0 1, [[1;0;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a six composants pour le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ; Rac1b_GDP___ ; Rac1b_GDP___ ; Rac1b_GDP___ ] -> Rac1b_GTP___ 0 1, [[1;0;0;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have three  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_3_4_5_P3_pm_ 0 -> AKT1____pm_ 1 0 
PI_3_4_5_P3_pm_ 1 -> AKT1____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> AKT1____pm_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> AKT1____pm_ 0 1, [[1;0;0]])(*here we have 3 predecessors*)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> AKT1____pm_ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
TNFR1A_BAG4_itm_ 0 -> BAG4 1 0 
TNFR1A_BAG4_itm_ 1 -> BAG4 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ TNFR1A_BAG4_itm_ ; TNFR1A_BAG4_itm_ ] -> BAG4 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ TNFR1A_BAG4_itm_ ; TNFR1A_BAG4_itm_ ] -> BAG4 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have 4  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MKK4____c_ 0 -> Jnk1___ 1 0 
MKK4____c_ 1 -> Jnk1___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ MKK4____c_ ; MKK4____c_ ] -> Jnk1___ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ MKK4____c_ ; MKK4____c_ ; MKK4____c_ ] -> Jnk1___ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ MKK4____c_ ; MKK4____c_ ; MKK4____c_ ; MKK4____c_ ] -> Jnk1___ 0 1, [[1;0;0;0]])(*here we have 4 predecessors*)(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ MKK4____c_ ; MKK4____c_ ; MKK4____c_ ; MKK4____c_ ] -> Jnk1___ 0 1, [[1;0;0;0]])(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GDP___ 0 -> Rac1b_GDP___ 1 0 
Rac1b_GDP___ 1 -> Rac1b_GDP___ 0 1 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
RAC1_GTP____pm_ 0 -> RAC1_GTP____pm_ 1 0 
RAC1_GTP____pm_ 1 -> RAC1_GTP____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have 4  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K 0 -> PI3K____pm_ 1 0 
PI3K 1 -> PI3K____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI3K ; PI3K ] -> PI3K____pm_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI3K ; PI3K ; PI3K ] -> PI3K____pm_ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ PI3K ; PI3K ; PI3K ; PI3K ] -> PI3K____pm_ 0 1, [[1;0;0;0]])(*here we have 4 predecessors*)(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MEKK1___ 0 -> MKK4____c_ 1 0 
MEKK1___ 1 -> MKK4____c_ 0 1 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_4_5_P2_pm_ 0 -> PI_4_5_P2_pm_ 1 0 
PI_4_5_P2_pm_ 1 -> PI_4_5_P2_pm_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_4_5_P2_pm_ 0 -> CDC42_GTP____pm_ 1 0 
PI_4_5_P2_pm_ 1 -> CDC42_GTP____pm_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MYC_Max_n_ 0 -> MYC_Max_MIZ_1_n_ 1 0 
MYC_Max_n_ 1 -> MYC_Max_MIZ_1_n_ 0 1 
(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
E_cadherin_beta_catenin_alpha_catenin_cj_ 0 -> RhoA_GDP___ 1 0 
E_cadherin_beta_catenin_alpha_catenin_cj_ 1 -> RhoA_GDP___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ E_cadherin_beta_catenin_alpha_catenin_cj_ ; E_cadherin_beta_catenin_alpha_catenin_cj_ ] -> RhoA_GDP___ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ E_cadherin_beta_catenin_alpha_catenin_cj_ ; E_cadherin_beta_catenin_alpha_catenin_cj_ ] -> RhoA_GDP___ 0 1, [[1;0]])(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GTP___ 0 -> MKK4___ 1 0 
Rac1b_GTP___ 1 -> MKK4___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GTP___ ; Rac1b_GTP___ ] -> MKK4___ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GTP___ ; Rac1b_GTP___ ] -> MKK4___ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Jnk1___ 0 -> Jun___ 1 0 
Jnk1___ 1 -> Jun___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
alpha_catenin_c_ 0 -> E_cadherin_beta_catenin_alpha_catenin_bpm_ 1 0 
alpha_catenin_c_ 1 -> E_cadherin_beta_catenin_alpha_catenin_bpm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have 5  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
JUN_JUND____n_ 0 -> MYC 1 0 
JUN_JUND____n_ 1 -> MYC 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ JUN_JUND____n_ ; JUN_JUND____n_ ] -> MYC 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ] -> MYC 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a cinq composants pour le pattern*) 
COOPERATIVITY([ JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ] -> MYC 0 1, [[1;0;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a six composants pour le pattern*) 
COOPERATIVITY([ JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ] -> MYC 0 1, [[1;0;0;0;0]])(*pattern *) 
(*ici on a six composants pour le pattern*) 
COOPERATIVITY([ JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ; JUN_JUND____n_ ] -> MYC 0 1, [[1;0;0;0;0]])(*une proteine*)
(*here we have three  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_3_4_5_P3_pm_ 0 -> AKT1____pm_ 1 0 
PI_3_4_5_P3_pm_ 1 -> AKT1____pm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> AKT1____pm_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> AKT1____pm_ 0 1, [[1;0;0]])(*here we have 3 predecessors*)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> AKT1____pm_ 0 1, [[1;0;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
IKK_beta___ 0 -> NF_kappa_B1_p50_RelA____c_ 1 0 
IKK_beta___ 1 -> NF_kappa_B1_p50_RelA____c_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
BAG4 0 -> TNFR1A_BAG4_itm_ 1 0 
BAG4 1 -> TNFR1A_BAG4_itm_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GTP___ 0 -> MKK4____c_ 1 0 
Rac1b_GTP___ 1 -> MKK4____c_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ ATF2____n_ ; ATF2____n_ ] -> ATF2_JUN____n_ 0 1, [[1;0]])(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
PDK1___ 0 -> PDK1___ 1 0 
PDK1___ 1 -> PDK1___ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active____n_ 0 -> ELK1_n_ 1 0 
Erk1_2_active____n_ 1 -> ELK1_n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
LEF1_beta_catenin____n_ 0 -> E_cadherin 1 0 
LEF1_beta_catenin____n_ 1 -> E_cadherin 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MYC_Max_n_ 0 -> MYC_Max_n_ 1 0 
MYC_Max_n_ 1 -> MYC_Max_n_ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
STAT1_3__dimer_ 0 -> STAT1_3__dimer____ 1 0 
STAT1_3__dimer_ 1 -> STAT1_3__dimer____ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GDP___ 0 -> RAC1_GTP____cj_ 1 0 
Rac1b_GDP___ 1 -> RAC1_GTP____cj_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ] -> RAC1_GTP____cj_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GDP___ ; Rac1b_GDP___ ] -> RAC1_GTP____cj_ 0 1, [[1;0]])(*un complex2*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Rac1b_GTP___ 0 -> Rac1b_GDP___ 1 0 
Rac1b_GTP___ 1 -> Rac1b_GDP___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GTP___ ; Rac1b_GTP___ ] -> Rac1b_GDP___ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Rac1b_GTP___ ; Rac1b_GTP___ ] -> Rac1b_GDP___ 0 1, [[1;0]])(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PDK1____pm_ ; PDK1____pm_ ] -> RALGDS____pm_ 0 1, [[1;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ EGFR__dimer_____itm_ ; EGFR__dimer_____itm_ ] -> PI3K_Class_IA_family____pm_ 0 1, [[1;0]])(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MYC 0 -> MYC_Max_n_ 1 0 
MYC 1 -> MYC_Max_n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
GSK3beta____c_ 0 -> Axin1____c_ 1 0 
GSK3beta____c_ 1 -> Axin1____c_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*on a deux composants pour le pattern*) 
c_Jun_n_ 0 -> c_Jun_n_ 1 0 
c_Jun_n_ 1 -> c_Jun_n_ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ IKK_complex_c_ ; IKK_complex_c_ ] -> IKK_beta___ 0 1, [[1;0]])(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MKK4____c_ 0 -> nPKC_delta___ 1 0 
MKK4____c_ 1 -> nPKC_delta___ 0 1 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
p38_alpha___ 0 -> STAT3__dimer____ 1 0 
p38_alpha___ 1 -> STAT3__dimer____ 0 1 
(*une proteine*)
(*here we have three  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
MKK4_MKK7___ 0 -> JNK_family_active___ 1 0 
MKK4_MKK7___ 1 -> JNK_family_active___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ MKK4_MKK7___ ; MKK4_MKK7___ ] -> JNK_family_active___ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ MKK4_MKK7___ ; MKK4_MKK7___ ; MKK4_MKK7___ ] -> JNK_family_active___ 0 1, [[1;0;0]])(*here we have 3 predecessors*)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ MKK4_MKK7___ ; MKK4_MKK7___ ; MKK4_MKK7___ ] -> JNK_family_active___ 0 1, [[1;0;0]])(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8_c_ 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8_c_ 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8_c_ 2 1 @1.25~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8_c_ 1 0 @0.5555556~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8_c_ 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8_c_ 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8_c_ 2 1 @1.25~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8_c_ 1 0 @0.5555556~50 


(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8_c_ 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
JUN_FOS____n_ 1 -> IL8_c_ 1 2 @5.0~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8_c_ 2 1 @1.25~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
JUN_FOS____n_ 0 -> IL8_c_ 1 0 @0.5555556~50 


(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ TNFR1A_BAG4 ; TNFR1A_BAG4 ] -> TNFR1A_BAG4_TNF_alpha 0 1, [[1;0]])(*une proteine*)
(*here we have two predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
Erk1_2_active____n_ 0 -> c_Jun_n_ 1 0 
Erk1_2_active____n_ 1 -> c_Jun_n_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Erk1_2_active____n_ ; Erk1_2_active____n_ ] -> c_Jun_n_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Erk1_2_active____n_ ; Erk1_2_active____n_ ] -> c_Jun_n_ 0 1, [[1;0]])(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
NF_kappa_B1_p50_RelA____n_ 1 -> A20 0 1 @10.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
NF_kappa_B1_p50_RelA____n_ 1 -> A20 1 2 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
NF_kappa_B1_p50_RelA____n_ 0 -> A20 2 1 @1.6666667~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
NF_kappa_B1_p50_RelA____n_ 0 -> A20 1 0 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
NF_kappa_B1_p50_RelA____n_ 0 -> A20 1 0 @0.5555556~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*here we have three  predecessors *)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_3_4_5_P3_pm_ 0 -> mTOR___ 1 0 
PI_3_4_5_P3_pm_ 1 -> mTOR___ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> mTOR___ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> mTOR___ 0 1, [[1;0;0]])(*here we have 3 predecessors*)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ; PI_3_4_5_P3_pm_ ] -> mTOR___ 0 1, [[1;0;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
TNFR1A_BAG4_itm_ 0 -> sTNF_alpha_TNFR1A____itm_ 1 0 
TNFR1A_BAG4_itm_ 1 -> sTNF_alpha_TNFR1A____itm_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
E_cadherin 0 -> E_cadherin_Ca2__beta_catenin_alpha_catenin_cj_ 1 0 
E_cadherin 1 -> E_cadherin_Ca2__beta_catenin_alpha_catenin_cj_ 0 1 
(*un complex2*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
(*on a un gene mesure*)(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> uPAR 0 1 @5.0~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> uPAR 1 2 @3.3333335~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
Erk1_2_active___ 0 -> uPAR 2 1 @1.25~50 


(*Nous sommes dans un cas Param*)

(*inibition*)
Erk1_2_active___ 0 -> uPAR 1 0 @0.8333334~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> uPAR 0 1 @0.5555556~50 


(*Nous sommes dans un cas Param*)

(*activation*)
Erk1_2_active___ 1 -> uPAR 0 1 @0.5555556~50 


(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; c_Jun_n_ ] -> JUN_JUN_FOS____n_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI_3_4_5_P3_cj_ 0 -> AKT1_2_active___ 1 0 
PI_3_4_5_P3_cj_ 1 -> AKT1_2_active___ 0 1 
(*une proteine*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*un  1 predecesseur*)
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(*on a deux composants pour le pattern*) 
PI3K 0 -> PI3K____cj_ 1 0 
PI3K 1 -> PI3K____cj_ 0 1 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*The initial state....*) 
 
initial_state 
JUN_JUND____n_ 0, JUN_FOS 0, LEF1_beta_catenin____n_ 0, RSK2___ 0, TNF_alpha_er_ 0, ATF2____n_ 0, RAC1_CDC42_GTP___ 0, cell_cycle_arrest 0, JUN_FOS____n_ 0, sTNF_alpha_TNFR1A_MADD____itm_ 0, uPAR__dimer__pm_ 0, PTEN 0, uPA_uPAR__dimer_____pm_ 0, AKT1___ 0, p38_alpha___ 0, Fos_n_ 0, GSK3beta____c_ 0, GAB1____pm_ 0, PDK1____pm_ 0, NF_kappa_B1_p50_RelA____n_ 0, DKK1 0, TfR 0, JNK_cascade 0, alpha_catenin_c_ 0, E_cadherin_c_ 0, ATF2 0, PI_3_4_5_P3_pm_ 0, ATF2_JUND_macroH2A_n_ 0, MEKK1___ 0, ET1 0, Hes5_n_ 0, EGFR__dimer_____itm_ 0, MKP1____n_ 0, IKK_complex_c_ 0, E_cadherin_lpm_ 0, PI_4_5_P2_pm_ 0, STAT3__dimer_____n_ 0, NF_kappa_B1_p50_RelA____c_ 0, ATF2_JUND____n_ 0, Rho_family_GTPase 0, JUN_FOS____n_ 0, beta_catenin_c_ 0, P110_alpha 0, Rho_Family_GTPase_active___ 0, CEBPA_BRM_RB1_E2F4_n_ 0, TNFR1A_BAG4 0, Src___ 0, RhoA_GTP___ 0, Erk1_2_active___ 0, Erk1_2_active____n_ 0, keratinocyte_differentiation 0, PIP5K1C____cj_ 0, PI3K_Class_IA_family___ 0, PI_3_4_5_P3_cj_ 0, NF_kappa_B1_p50_RelA_I_kappa_B_alpha_n_ 0, MKK4_MKK7___ 0, MAPKKK_cascade 0, IL1_beta 0, MKP3___ 0, Jun____n_ 0, cell_adhesion 0, ELK1_SRF____n_ 0, RB1 0, RSK2 0, STAT1_3__dimer_ 0, MKP1 0, STAT3____c_ 0, PI_4_5_P2_cj_ 0, NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 0, IL8 0, PIP3_pm_ 0, E_cadherin_itm_ 0, IKK_alpha_c_ 0, RAC1_GTP____pm_ 0, E_cadherin_i_ 1, PI3K 0, RALA_GTP____pm_ 0, PDK1___ 0, SM22 0, beta_catenin_n_ 0, Rac1b_GTP___ 0, AKT1____pm_ 0, BAG4 0, Jnk1___ 0, PI3K____pm_ 0, MKK4____c_ 0, CDC42_GTP____pm_ 0, MYC_Max_MIZ_1_n_ 0, RhoA_GDP___ 0, MKK4___ 0, Jun___ 0, JUN_FOS_n_ 0, E_cadherin_beta_catenin_alpha_catenin_bpm_ 0, FOS 0, MYC 0, AKT1____pm_ 0, NF_kappa_B1_p50_RelA____c_ 0, TNFR1A_BAG4_itm_ 0, MKK4____c_ 0, ATF2_JUN____n_ 0, ELK1_n_ 0, E_cadherin 0, MYC_Max_n_ 0, STAT1_3__dimer____ 0, RAC1_GTP____cj_ 0, Rac1b_GDP___ 0, RALGDS____pm_ 0, PI3K_Class_IA_family____pm_ 0, MYC_Max_n_ 0, HRAS_GTP____pm_ 0, Axin1____c_ 0, IKK_beta___ 0, nPKC_delta___ 0, STAT3__dimer____ 0, JNK_family_active___ 0, IL8_c_ 0, TNFR1A_BAG4_TNF_alpha 0, c_Jun_n_ 0, E_cadherin_beta_catenin_alpha_catenin_cj_ 0, A20 0, mTOR___ 0, sTNF_alpha_TNFR1A____itm_ 0, E_cadherin_Ca2__beta_catenin_alpha_catenin_cj_ 0, NF_kappa_B1_p50_RelA____n_ 0, uPAR 0, JUN_JUN_FOS____n_ 0, AKT1_2_active___ 0, IL8_er_ 0, PI3K____cj_ 0, 
