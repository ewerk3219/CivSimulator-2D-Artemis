package com.sim.resourceLoaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.sim.itemData.material.Material;
import com.sim.itemData.material.MaterialName;
import com.sim.itemData.material.MaterialType;
import com.sim.itemData.wearables.Armor;
import com.sim.itemData.wearables.ArmorName;
import com.sim.itemData.wearables.DmgType;
import com.sim.itemData.wearables.Size;
import com.sim.itemData.wearables.Weapon;
import com.sim.itemData.wearables.WeaponName;
import com.sim.itemData.wearables.WearableSlot;

public class ItemDataLoader {
	public static final int TOTAL_NUM_OF_SIZES = 3;
	private HashMap<MaterialName, Material> itemTemplateDataMaterial;

	private HashMap<Size, HashMap<ArmorName, Armor>> itemTemplateDataArmor;
	private HashMap<Size, HashMap<WeaponName, Weapon>> itemTemplateDataWeapon;

	public static final String itemDataPath = "res/ItemData/";

	public ItemDataLoader() {
		this.itemTemplateDataMaterial = new HashMap<MaterialName, Material>();
		this.itemTemplateDataArmor = new HashMap<Size, HashMap<ArmorName, Armor>>();
		this.itemTemplateDataWeapon = new HashMap<Size, HashMap<WeaponName, Weapon>>();

		collectMaterialData();
		collectArmorData();
		collectWeaponData();
	}

	private void collectMaterialData() {
		File crystalData = new File(itemDataPath + "Material/Crystal Data.txt");
		try {
			Scanner crystalScanner = new Scanner(crystalData);
		} catch (FileNotFoundException e) {
			System.out.println("Crystal Data not found");
			e.printStackTrace();
		}

		// ... more here
	}

	private void collectArmorData() {
		this.itemTemplateDataArmor.put(Size.Small, new HashMap<ArmorName, Armor>());
		this.itemTemplateDataArmor.put(Size.Medium, new HashMap<ArmorName, Armor>());
		this.itemTemplateDataArmor.put(Size.Large, new HashMap<ArmorName, Armor>());

		File armorData = new File(itemDataPath + "Wearables/armor.txt");
		Scanner dataScanner = null;

		try {
			dataScanner = new Scanner(armorData);
		} catch (FileNotFoundException e) {
			System.out.println("Armor data not found.");
			e.printStackTrace();
		}

		while (dataScanner.hasNextLine()) {
			// Skip over #
			if (dataScanner.hasNextLine())
				dataScanner.nextLine();

			Armor armorSmall = new Armor();
			Armor armorMedium = new Armor();
			Armor armorLarge = new Armor();

			armorSmall.setSize(Size.Small);
			armorMedium.setSize(Size.Medium);
			armorLarge.setSize(Size.Large);

			Armor[] aArray = new Armor[TOTAL_NUM_OF_SIZES];
			aArray[0] = armorSmall;
			aArray[1] = armorMedium;
			aArray[2] = armorLarge;

			ArmorName name = ArmorName.valueOf(dataScanner.nextLine());
			MaterialType materialAllowed = MaterialType.valueOf(dataScanner.nextLine());
			int armorClass = dataScanner.nextInt();
			dataScanner.nextLine();

			for (int armorIndex = 0; armorIndex < TOTAL_NUM_OF_SIZES; armorIndex++) {
				float weight = dataScanner.nextFloat();
				if (dataScanner.hasNextLine())
					dataScanner.nextLine();
				aArray[armorIndex].setName(name);
				aArray[armorIndex].setMaterialTypeAllowed(materialAllowed);
				aArray[armorIndex].setArmorClass(armorClass);
				aArray[armorIndex].setWeight(weight);

				// Load into database
				this.itemTemplateDataArmor.get(aArray[armorIndex].getSize())
						.put(aArray[armorIndex].getName(), aArray[armorIndex]);
			}
		}
		dataScanner.close();
	}

	private void collectWeaponData() {
		this.itemTemplateDataWeapon.put(Size.Small, new HashMap<WeaponName, Weapon>());
		this.itemTemplateDataWeapon.put(Size.Medium, new HashMap<WeaponName, Weapon>());
		this.itemTemplateDataWeapon.put(Size.Large, new HashMap<WeaponName, Weapon>());

		File weaponData = new File(itemDataPath + "Wearables/weapons.txt");
		Scanner dataScanner = null;
		try {
			dataScanner = new Scanner(weaponData);
		} catch (FileNotFoundException e) {
			System.out.println("Weapon data not found.");
			e.printStackTrace();
		}
		while (dataScanner.hasNextLine()) {
			Weapon weaponSmall = new Weapon();
			weaponSmall.setWeaponSize(Size.Small);
			Weapon weaponMedium = new Weapon();
			weaponMedium.setWeaponSize(Size.Medium);
			Weapon weaponLarge = new Weapon();
			weaponLarge.setWeaponSize(Size.Large);

			// Skip over "#"
			dataScanner.nextLine();
			String weaponName = dataScanner.nextLine();
			WeaponName name = WeaponName.valueOf(weaponName);
			weaponSmall.setName(name);
			weaponMedium.setName(name);
			weaponLarge.setName(name);

			Weapon[] wArray = new Weapon[TOTAL_NUM_OF_SIZES];
			wArray[0] = weaponSmall;
			wArray[1] = weaponMedium;
			wArray[2] = weaponLarge;

			// Get damage stats
			for (int weaponIndex = 0; weaponIndex < TOTAL_NUM_OF_SIZES; weaponIndex++) {
				String dmgData = dataScanner.nextLine();
				int indexOfD = dmgData.indexOf('d');
				int rollCount = Integer.parseInt(dmgData.substring(0, indexOfD));
				int rollMagnitude = Integer.parseInt(dmgData.substring(indexOfD + 1));

				wArray[weaponIndex].setRollCount(rollCount);
				wArray[weaponIndex].setRollMagnitude(rollMagnitude);
			}
			int crit = dataScanner.nextInt();
			dataScanner.nextLine();
			DmgType dmgType = DmgType.valueOf(dataScanner.nextLine());
			int range = dataScanner.nextInt();
			dataScanner.nextLine();
			WearableSlot wearableSlot = WearableSlot.valueOf(dataScanner.nextLine());
			int slotUseNum = dataScanner.nextInt();

			for (int weaponIndex = 0; weaponIndex < TOTAL_NUM_OF_SIZES; weaponIndex++) {
				wArray[weaponIndex].setCritDamageMultiplier(crit);
				wArray[weaponIndex].setDamageType(dmgType);
				wArray[weaponIndex].setRange(range);
				wArray[weaponIndex].setWearableSlot(wearableSlot);
				wArray[weaponIndex].setSlotNumbersUsed(slotUseNum);
				float weight = dataScanner.nextFloat();
				wArray[weaponIndex].setWeight(weight);
			}
			if (dataScanner.hasNextLine()) {
				dataScanner.nextLine();
			}

			// Load into database
			for (int weaponIndex = 0; weaponIndex < TOTAL_NUM_OF_SIZES; weaponIndex++) {
				this.itemTemplateDataWeapon.get(wArray[weaponIndex].getWeaponSize())
						.put(wArray[weaponIndex].getName(), wArray[weaponIndex]);
			}
		}
		dataScanner.close();
	}
}