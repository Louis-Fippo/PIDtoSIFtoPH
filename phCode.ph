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
COOPERATIVITY([ Jun___ ; JNK_family_active___ ] -> JUN_JUND____n_ 0 1, [[1;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ Jun___ ; JNK_family_active___ ] -> JUN_JUND____n_ 0 1, [[1;0]])(*un compound*)
(*un  1 predecesseur*)
(*ici on a un predecesseur*)
(*pattern *) 
(* composant tout seul*) 
(*un complex2*)
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a trois composant dans le pattern*) 
COOPERATIVITY([ c_Jun_n_ ; Fos_n_ ] -> JUN_FOS 0 1, [[1;0]])(*un compound*)
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
PI3K____pm_ 0 -> NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 1 0 
PI3K____pm_ 1 -> NF_kappa_B1_p50_RelA_I_kappa_B_alpha_c_ 0 1 
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
(*here we have two predecessors *)
(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ Jnk1___ ; ATF2 ; p38_alpha___ ] -> ATF2____n_ 0 1, [[1;0;0]])(*here we are in the case where we have 2 predecessors *)(*pattern *) 
(*ici on a quatre composants pour le pattern*) 
COOPERATIVITY([ Jnk1___ ; ATF2 ; p38_alpha___ ] -> ATF2____n_ 0 1, [[1;0;0]])(*un complex2*)
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
COOPERATIVITY([ STAT3__dimer_____n_ ; MYC_Max_MIZ_1_n_ ] -> cell_cycle_arrest 0 1, [[1;0]])(*un compound*)
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