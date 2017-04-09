package com.sim.subSystems.entity.components;

import com.artemis.Component;

public class CharacterSheet extends Component {

	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;

	private int baseAttack;
	private int ArmorClass;

	public CharacterSheet() {
		super();
	}

	public CharacterSheet(int st, int de, int co, int in, int wi, int ch, int bA,
			int aC) {
		this.strength = st;
		this.dexterity = de;
		this.constitution = co;
		this.intelligence = in;
		this.wisdom = wi;
		this.charisma = ch;
		this.baseAttack = bA;
		this.ArmorClass = aC;
	}

	public int getBaseAttack() {
		return baseAttack;
	}

	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}

	public int getArmorClass() {
		return ArmorClass;
	}

	public void setArmorClass(int armorClass) {
		ArmorClass = armorClass;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getConstitution() {
		return constitution;
	}

	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}

	public int getCharisma() {
		return charisma;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	private int computeMod(int baseStat) {
		int num = baseStat - 10;
		num = (int) Math.ceil(num / 2);
		return num;
	}

	public int getStrengthMod() {
		return computeMod(strength);
	}

	public int getDexterityMod() {
		return computeMod(dexterity);
	}

	public int getConstitutionMod() {
		return computeMod(constitution);
	}

	public int getIntelligenceMod() {
		return computeMod(intelligence);
	}

	public int getWisdomMod() {
		return computeMod(wisdom);
	}

	public int getCharismaMod() {
		return computeMod(charisma);
	}
}