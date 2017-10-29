package com.reparadoras.caritas.ui.utils;

import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.HomeType;
import com.reparadoras.caritas.model.JobSituation;
import com.reparadoras.caritas.model.Studies;

public class Constants {

	public static String getNemonicFamilyType(FamilyType familyType) {
		try {
			if (familyType.getDescription().equals("Sola")) {
				return "S";
			} else if (familyType.getDescription().equals("Pareja con Hijos")) {
				return "PCH";
			} else if (familyType.getDescription().equals("Pareja sin Hijos")) {
				return "PSH";
			} else if (familyType.getDescription().equals("Monoparental")) {
				return "M";
			} else if (familyType.getDescription().equals("Otra")) {
				return "O";
			} else {
				return "S";
			}
		} catch (Exception e) {
			return "S";
		}

	}

	public static String getNemonicAuthorizationType(AuthorizationType aType) {
		try {
			if (aType.getDescription().equals("Autorización Residencia")) {
				return "AR";
			} else if (aType.getDescription().equals("Autorización Residencia y Trabajo")) {
				return "ART";
			} else if (aType.getDescription().equals("Estudios")) {
				return "E";
			} else if (aType.getDescription().equals("Turismo")) {
				return "T";
			} else if (aType.getDescription().equals("Refugiado")) {
				return "R";
			} else {
				return "AR";
			}
		} catch (Exception e) {
			return "AR";
		}

	}

	public static String getNemonicJobSituation(JobSituation jType) {

		try {
			if (jType.getDescription().equals("Parado")) {
				return "P";
			} else if (jType.getDescription().equals("Con Trabajo Normalizado")) {
				return "TN";
			} else if (jType.getDescription().equals("Con Trabajo Marginal o Economia Sumergida")) {
				return "TM";
			} else if (jType.getDescription().equals("Labores del hogar (ama de casa)")) {
				return "Ama de casa";
			} else if (jType.getDescription().equals("Pensionista o Jubilado")) {
				return "Pe";
			} else if (jType.getDescription().equals("Otros inactivos (estudiantes, menores")) {
				return "O";
			} else
				return "P";

		} catch (Exception e) {
			return "P";
		}
	}

	public static String getNemonicStudies(Studies sType) {
		try {
			if (sType.getDescription().equals("No sabe leer ni escribir")) {
				return "NLE";
			} else if (sType.getDescription().equals("Sólo sabe leer y escribir")) {
				return "SLE";
			} else if (sType.getDescription().equals("Infantil")) {
				return "I";
			} else if (sType.getDescription().equals("Primaria")) {
				return "P";
			} else if (sType.getDescription().equals("Secundaria")) {
				return "S";
			} else if (sType.getDescription().equals("Bachillerato")) {
				return "B";
			} else if (sType.getDescription().equals("FP-Grado Medio")) {
				return "FP-GM";
			} else if (sType.getDescription().equals("FP-Grado Superior")) {
				return "FP-GS";
			} else if (sType.getDescription().equals("Universidad Diplomado")) {
				return "UL";
			} else
				return "NLE";
		} catch (Exception e) {
			return "NLE";
		}

	}

	public static AuthorizationType getAuthorizationType(String nemonic) {
		AuthorizationType aType = new AuthorizationType();
		if (nemonic.equals("AR")) {
			aType.setDescription("Autorización Residencia");

		} else if (nemonic.equals("ART")) {
			aType.setDescription("Autorización Residencia y Trabajo");

		} else if (nemonic.equals("E")) {
			aType.setDescription("Estudios");
		} else if (nemonic.equals("T")) {
			aType.setDescription("Turismo");
		} else if (nemonic.equals("R")) {
			aType.setDescription("Refugiado");
		} else
			aType.setDescription("Autorización Residencia");

		return aType;
		// return authorizationTypeDAO.findAuthorizationType(aType);

	}

	public static JobSituation getJobSituation(String nemonic) {
		JobSituation jType = new JobSituation();
		if (nemonic.equals("P")) {
			jType.setDescription("Parado");

		} else if (nemonic.equals("TN")) {
			jType.setDescription("Con Trabajo Normalizado");

		} else if (nemonic.equals("TM")) {
			jType.setDescription("Con Trabajo Marginal o Economia Sumergida");
		} else if (nemonic.equals("Ama de casa")) {
			jType.setDescription("Labores del hogar (ama de casa)");
		} else if (nemonic.equals("Pe")) {
			jType.setDescription("Pensionista o Jubilado");
		} else if (nemonic.equals("O")) {
			jType.setDescription("Otros inactivos (estudiantes, menores");
		} else
			jType.setDescription("Parado");

		return jType;
		// return jobSituationDAO.findJobSituation(jType);

	}

	public static Studies getStudies(String nemonic) {
		Studies sType = new Studies();
		if (nemonic.equals("NLE")) {
			sType.setDescription("No sabe leer ni escribir");

		} else if (nemonic.equals("SLE")) {
			sType.setDescription("Sólo sabe leer y escribir");

		} else if (nemonic.equals("I")) {
			sType.setDescription("Infanil");
		} else if (nemonic.equals("P")) {
			sType.setDescription("Primaria");
		} else if (nemonic.equals("S")) {
			sType.setDescription("Secundaria");
		} else if (nemonic.equals("B")) {
			sType.setDescription("Bachillerato");
		} else if (nemonic.equals("FP-GM")) {
			sType.setDescription("FP-Grado Medio");
		} else if (nemonic.equals("FP-GS")) {
			sType.setDescription("FP-Grado Superior");
		} else if (nemonic.equals("UL")) {
			sType.setDescription("Universidad Diplomado");
		}

		else
			sType.setDescription("No sabe leer ni escribir");

		return sType;
		// return studiesDAO.findStudies(sType);

	}

	public static FamilyType getFamilyType(String nemonic) {
		FamilyType fType = new FamilyType();
		if (nemonic.equals("S")) {
			fType.setDescription("Sola");

		} else if (nemonic.equals("PCH")) {
			fType.setDescription("Pareja con Hijos");

		} else if (nemonic.equals("PSH")) {
			fType.setDescription("Pareja sin Hijos");
		} else if (nemonic.equals("M")) {
			fType.setDescription("Monoparental");
		} else if (nemonic.equals("O")) {
			fType.setDescription("Otra");
		} else
			fType.setDescription("Sola");

		return fType;
		// return familyTypeDAO.findFamilyType(fType);

	}

	public static HomeType getHomeType(String nemonic) {
		HomeType hType = new HomeType();
		if (nemonic.equals("S")) {
			hType.setDescription("Piso");

		} else if (nemonic.equals("PCH")) {
			hType.setDescription("Familiar");

		} else if (nemonic.equals("PSH")) {
			hType.setDescription("Habitación");
		} else if (nemonic.equals("M")) {
			hType.setDescription("Estudio");
		} else
			hType.setDescription("Piso");

		return hType;
		// return familyTypeDAO.findFamilyType(fType);

	}

}
