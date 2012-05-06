package com.forgewareinc.Cinema;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SandstoneType;

public class Color {
	static SandstoneType sstsmooth = SandstoneType.SMOOTH;
	public static Color[] colors = new Color[]{
		//new Color(147,147,147,,0),//strange grass top?
		new Color(122,122,122,Material.COBBLESTONE.getId(),(byte)0),//cobblestone
		new Color(143,139,124,Material.GOLD_ORE.getId(),(byte)0),//goldore
		new Color(182,182,57,Material.SPONGE.getId(),(byte)0),//sponge
		//new Color(221,221,221,,0),//white wool
		//new Color(130,106,58,,0),//torch
		//new Color(106,89,64,,0),//lever
		//new Color(120,107,86,,0),//rail curve
		//new Color(121,108,88,,0),//rail
		new Color(29,71,165,Material.LAPIS_BLOCK.getId(),(byte)0),//lapis block
		new Color(102,112,134,Material.LAPIS_ORE.getId(),(byte)0),//lapis ore
		//new Color(218,210,158,,0),//sandstonetop
		new Color(216,209,157,Material.SANDSTONE.getId(),SandstoneType.CRACKED.getData()),//sandstone
		//new Color(212,205,148,,0),//ssandcracks???
		new Color(44,22,26,Material.NETHER_BRICK.getId(),(byte)0),//netherbrick
		//new Color(137,137,137,,0),//crack 1
		//0
		new Color(125,125,125,Material.STONE.getId(),(byte)0),//cleanstone
		//new Color(83,83,83,,0),//bedrock. better dont use
		new Color(135,130,126,Material.IRON_ORE.getId(),(byte)0),//iron ore
		//new Color(218,240,244,,0),//glass
		//new Color(26,39,49,,0),//spawner
		//new Color(134,103,51,,0),//wood door up
		//new Color(134,101,50,,0),//wood dor down
		//new Color(25,22,22,,0),//black wool
		//new Color(150,52,48,,0),//red wool
		//new Color(53,70,27,,0),//olive 
		//new Color(79,50,31,,0),//brown
		//new Color(46,56,141,,0),//blue
		//new Color(126,61,181,,0),//purple
		//new Color(46,110,137,,0),//cyan
		//new Color(154,161,161,,0),//grey wool
		//new Color(112,112,112,,0),//crack2
		//1
		new Color(134,96,67,Material.DIRT.getId(),(byte)0),//dirt
		new Color(219,211,160,Material.SAND.getId(),(byte)0),//sand
		new Color(115,115,115,Material.COAL_ORE.getId(),(byte)0),//coal ore
		new Color(129,140,143,Material.DIAMOND_ORE.getId(),(byte)0),//diamond ore
		new Color(239,251,251,Material.SNOW_BLOCK.getId(),(byte)0),//snow
		//new Color(186,186,186,,0),//iron door up
		//new Color(163,163,163,,0),//iron door down
		//new Color(64,64,64,,0),//dark grey wool
		//new Color(208,132,153,,0),//pink
		//new Color(65,174,56,,0),//green
		//new Color(177,166,39,,0),//yellow
		//new Color(106,138,201,,0),//light blue
		//new Color(179,80,188,,0),//dark pink
		//new Color(219,125,62,,0),//orange wool
		//new Color(106,14,30,,0),//nether shroom 1
		//new Color(105,105,105,,0),//crack 3
		//2
		//new Color(126,107,65,,0),//grass side? dotn use cause up is totaly green or diff color
		new Color(131,123,123,Material.GRAVEL.getId(),(byte)0),//gravel
		//new Color(107,88,57,,0),//bookshelf
		new Color(132,107,107,Material.REDSTONE_ORE.getId(),(byte)0),//redstone ore
		new Color(125,172,254,Material.ICE.getId(),(byte)0),//ice
		//new Color(121,95,52,,0),//ladder
		//new Color(167,75,41,,0),//red torch on
		//new Color(93,62,38,,0),//red torch off
		//new Color(151,147,147,,0),//diode off
		//new Color(161,147,147,,0),//diode on
		//new Color(132,108,72,,0),//power rail off
		//new Color(154,104,70,,0),//power rail on
		//new Color(120,101,89,,0),//detector rail
		new Color(70,43,26,Material.REDSTONE_LAMP_OFF.getId(),(byte)0),//redstone lamp off
		//new Color(108,15,22,,0),//nether shroom 2
		//new Color(106,106,106,,0),//crack 4
		//3
		new Color(156,127,78,Material.WOOD.getId(),(byte)0),//wood planks
		//new Color(102,81,49,,0),//wood sides
		new Color(103,121,103,Material.MOSSY_COBBLESTONE.getId(),(byte)0),//mossy cobble
		//new Color(135,135,135,,0),//??
		//new Color(149,120,97,,0),//snow dirt
		//new Color(126,93,45,,0),//trapdoor
			//new Color(114,119,106,?????????????,0),//mossy stonebrick<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//new Color(45,28,12,,0),//dark wood sides
		//new Color(116,116,116,,0),//??
		//new Color(211,239,244,,0),//glasspane side
		//new Color(239,239,239,,0),//cobweb
		//new Color(-2147483648,-2147483648,-2147483648,,0),//total alpha
		//new Color(145,142,134,,0),//??
		new Color(119,89,55,Material.REDSTONE_LAMP_ON.getId(),(byte)0),//redstone lamp on
		//new Color(111,18,17,,0),//nether shroom 3
		//new Color(105,105,105,,0),// crack 5
		//4
		new Color(166,166,166,Material.DOUBLE_STEP.getId(),(byte)0),//double slap
		//new Color(154,125,77,,0),//log top
		new Color(20,18,29,Material.OBSIDIAN.getId(),(byte)0),//obsidian
		//new Color(99,99,99,,0),//??
		//new Color(13,99,24,,0),//cactus top
		//new Color(109,108,106,,0),//iron bars
			//new Color(118,118,118,,0),//cracked stone brick<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//new Color(206,206,201,,0),//birch log side
		//new Color(91,91,91,,0),//??
		//new Color(129,50,28,,0),//bed front
		//new Color(233,233,233,,0),//cobweb small
		//new Color(-2147483648,-2147483648,-2147483648,,0),//total alpha
		//new Color(130,128,121,,0),//??
			//new Color(118,118,118,,0),//stonebrick circly ?<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		new Color(215,208,154,Material.SANDSTONE.getId(),SandstoneType.GLYPHED.getData()),//glyphed sandstone
		//new Color(104,104,104,,0),//crack 6
		//5
		//new Color(159,159,159,,0),//slap top
		new Color(219,219,219,Material.IRON_BLOCK.getId(),(byte)0),//iron block
		//new Color(143,143,143,,0),//??
		new Color(122,122,122,Material.BRICK.getId(),(byte)0),//stone brick
		//new Color(13,102,24,,0),//cactus side
		//new Color(75,40,13,,0),//field wet
		//new Color(192,118,21,,0),//pumpkin up
		new Color(197,120,23,Material.PUMPKIN.getId(),(byte)0),//pumpkin side
		//new Color(142,22,22,,0),// bed front top
		//new Color(128,45,26,,0),//bed front side
		//new Color(103,64,59,,0),//magic book top
		//new Color(41,43,46,,0),//magic book side
		//new Color(103,77,46,,0),//dark planks
		//new Color(195,179,123,,0),//light planks
		new Color(219,211,161,Material.SANDSTONE.getId(),SandstoneType.SMOOTH.getData()),//sandstone smooth
		//new Color(102,102,102,,0),//crack 7
		//6
		new Color(146,99,86,Material.CLAY_BRICK.getId(),(byte)0),//brick
		new Color(249,236,78,Material.GOLD_BLOCK.getId(),(byte)0),//gold block
		//new Color(116,116,116,,0),//??
		//new Color(123,79,25,,0),//??
		//new Color(162,186,122,,0),//cactus cut
		//new Color(115,75,45,,0),//field not wet
		new Color(111,54,52,Material.NETHERRACK.getId(),(byte)0),//nether rack
		//new Color(142,76,12,,0),//pumpkin front
		//new Color(175,116,116,,0),//bed top top
		//new Color(150,102,84,,0),// bed top side
		//new Color(12,9,15,,0),//very dark purple thing
		//new Color(18,16,27,,0),//magic book bottom
		//new Color(154,110,77,,0),//teak planks
		//new Color(201,119,239,,0),//unused
		//new Color(201,119,239,,0),//unused
		//new Color(103,103,103,,0),//crack 8
		//7
		new Color(169,92,71,Material.TNT.getId(),(byte)0),//tnt side
		new Color(97,219,213,Material.DIAMOND_BLOCK.getId(),(byte)0),//diamond block
		//new Color(145,145,145,,0),//??
		//new Color(120,120,120,,0),//??
		new Color(158,164,176,Material.CLAY.getId(),(byte)0),//clay
		//new Color(0,179,18,,0),//wheat 1
		new Color(84,64,51,Material.SOUL_SAND.getId(),(byte)0),//soulsand
		//new Color(185,133,28,,0),//pumpkin light front
		new Color(141,145,36,Material.MELON_BLOCK.getId(),(byte)0),//melon side
		//new Color(175,160,139,,0),//bed read side
		//new Color(201,119,239,,0),//unused
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),//unused
		//new Color(102,102,102,,0),//crack 9
		//8
		//new Color(130,65,47,,0),//tnt top
		//new Color(130,94,37,,0),//chest top
		//new Color(129,94,39,,0),//big chest front
		//new Color(129,93,37,,0),//big chest read
		//new Color(148,192,101,,0),//sugar cane
		//new Color(18,172,15,,0),//wheat 2
		new Color(143,118,69,Material.GLOWSTONE.getId(),(byte)0),//glowstone 
		//new Color(228,205,206,,0),//cake top
		//new Color(151,153,36,,0),//melon top
		//new Color(87,67,26,,0),//teak log
		//new Color(201,119,239,,0),//unused
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),//unused
		//new Color(101,101,101,,0),//crack 10
		//9
		//new Color(170,77,51,,0),//tnt bottom
		//new Color(123,89,37,Material.CHEST.getId(),(byte)0),//chest side
		//chests kept crashing the game...
		//new Color(126,91,38,,0),//big chest front
		//new Color(127,92,36,,0),//big chest rear
		new Color(100,67,50,Material.JUKEBOX.getId(),(byte)0),//jukebox
		//new Color(28,137,11,,0),//wheat 3
		//new Color(141,146,99,,0),//sticky piston plate
		//new Color(188,141,113,,0),//cake side
		//new Color(69,69,69,,0),//cauldron top
		//new Color(62,62,62,,0),//cauldron side
		//new Color(201,119,239,,0),//unused
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),//unused
		//new Color(0,0,0,,0),//blah icons?
		//10
		//new Color(220,220,220,,0),//cobweb
		//new Color(122,90,40,,0),//chest front
		//new Color(107,71,42,,0),//workbench top
		new Color(118,95,60,Material.WORKBENCH.getId(),(byte)0),//workbench side 1
		//new Color(107,73,55,,0),//jukebox top
		//new Color(37,139,8,,0),//wheat 4
		//new Color(153,129,89,,0),//piston plate
		//new Color(132,56,26,,0),//cake cut
		//new Color(55,55,55,,0),//cauldron up
		//new Color(44,44,44,,0),//cauldron feet
		//new Color(201,119,239,,0),//unused
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),
		//new Color(201,119,239,,0),//unused
		//new Color(164,164,164,,0),//glass graphics
		//11
		/*new Color(138,43,13,,0),//red rose
		new Color(195,53,56,,0),//red shroom
		new Color(78,78,78,,0),//stove front
		new Color(115,95,63,,0),//workbench side 2
		new Color(118,118,118,,0),//lilly leaf
		new Color(46,128,7,,0),//wheat 5
		new Color(106,102,95,,0),//piston side
		new Color(177,89,36,,0),//??
		new Color(169,141,129,,0),//cake icon
		new Color(106,106,106,,0),//alchemy thing 
		new Color(201,119,239,,0),//unused
		new Color(201,119,239,,0),
		new Color(201,119,239,,0),
		new Color(201,119,239,,0),
		new Color(201,119,239,,0),//unused
		new Color(163,163,163,,0),//glass graphics*/
		//12
		//new Color(108,162,0,,0),//yellow flower
		//new Color(138,105,83,,0),//brown shroom
		new Color(113,113,113,Material.DISPENSER.getId(),(byte)0),//stove side//dispenser
		//new Color(125,102,85,,0),//stove on 
		new Color(113,88,73,Material.MYCEL.getId(),(byte)0),//mycelium side
		/*new Color(66,123,7,,0),//wheat 6
		new Color(96,96,96,,0),//piston bottom
		new Color(182,37,36,,0),//flyshroom side
		new Color(207,204,194,,0),//shroom inner
		new Color(124,103,81,,0),//??
		new Color(201,119,239,,0),
		new Color(201,119,239,,0),
		new Color(41,93,254,,0),//water
		new Color(201,119,239,,0),
		new Color(245,65,0,,0),//lava
		new Color(255,255,255,,0),*/
		//13
		/*new Color(0,42,255,,0),//BLUE??
		new Color(48,86,18,,0),//sapling
		new Color(116,116,116,,0),//dispenser front
		new Color(96,96,96,,0),//stove dispenser top down?
		new Color(111,99,105,,0),//mycel top
		new Color(80,118,7,,0),//wheat 7
		new Color(96,96,96,,0),//piston top side blah
		new Color(141,106,83,,0),//footprints???
		new Color(202,171,120,,0),//shroom inside 2
		new Color(89,117,96,,0),//end portal thing
		new Color(40,72,67,,0),//ender eye
		new Color(201,119,239,,0),//unused
		new Color(41,93,254,,0),//water 
		new Color(41,93,254,,0),//water
		new Color(245,65,0,,0),//lava
		new Color(245,65,0,,0),//lava*/
		//14
		/*new Color(71,102,37,,0),
		new Color(255,0,0,,0),
		new Color(255,0,0,,0),
		new Color(51,58,33,,0),
		new Color(118,150,84,,0),
		new Color(86,102,7,,0),
		new Color(153,153,153,,0),
		new Color(139,139,139,,0),
		new Color(111,111,111,,0),
		new Color(147,160,123,,0),*/
		new Color(221,223,165,121,(byte)0),//end floor
		/*new Color(201,119,239,,0),
		new Color(41,93,254,,0),
		new Color(41,93,254,,0),
		new Color(245,65,0,,0),
		new Color(245,65,0,,0),*/
		//15

		//wools
		new Color(23,23,23,Material.WOOL.getId(), DyeColor.BLACK.getData()),
		new Color(220,220,220,Material.WOOL.getId(),DyeColor.WHITE.getData()),
		new Color(150,50,50,Material.WOOL.getId(),DyeColor.RED.getData()),
		new Color(78,50,31,Material.WOOL.getId(),DyeColor.BROWN.getData()),
		new Color(175,165,40,Material.WOOL.getId(),DyeColor.YELLOW.getData()),
		new Color(125,60,180,Material.WOOL.getId(),DyeColor.PURPLE.getData()),
		new Color(45,110,135,Material.WOOL.getId(),DyeColor.CYAN.getData()),
		new Color(220,125,60,Material.WOOL.getId(),DyeColor.ORANGE.getData()),
		
		new Color(63,63,63,Material.WOOL.getId(),DyeColor.GRAY.getData()),//dark gray
		new Color(156,156,156,Material.WOOL.getId(),DyeColor.SILVER.getData()),//light gray
		
		new Color(180,80,190,Material.WOOL.getId(),DyeColor.MAGENTA.getData()),//dark pink
		new Color(200,125,150,Material.WOOL.getId(),DyeColor.PINK.getData()),//light pink
		
		new Color(50,70,25,Material.WOOL.getId(),DyeColor.GREEN.getData()),//dark green
		new Color(65,175,55,Material.WOOL.getId(),DyeColor.LIME.getData()),//light green
		
		new Color(45,55,140,Material.WOOL.getId(),DyeColor.BLUE.getData()),//dark blue
		new Color(100,135,200,Material.WOOL.getId(),DyeColor.LIGHT_BLUE.getData())
	};
	
	public int r,g,b,matID;
	public byte data;
	public Color(int r,int g,int b,int matID,byte data){
		this.r=r;
		this.g=g;
		this.b=b;
		this.matID = matID;
		this.data = data;
	}
	
	public Color(int r,int g,int b){
		this(r,g,b,0,(byte)0);
	}
	
	public static myBlock fromColor(int x, int y, int z, int rgba){
		int red = (rgba >> 16) & 0xFF; //R-Wert
        int green =(rgba >> 8) & 0xFF; //G-Wert
        int blue = (rgba >> 0) & 0xFF; //B-Wert
        
        Color now = new Color(red,green,blue);
		Color nearest=null;
		int nearestint=Integer.MAX_VALUE;
		for(int i=0;i<Color.colors.length;i++){
			int temp = now.distance(Color.colors[i]);
			if(temp<nearestint){
				nearestint = temp;
				nearest = Color.colors[i];
			}
		}
		return new myBlock(x, y, z, Material.getMaterial(nearest.matID), nearest.data);
	}
	
	public int distance(Color other){
		int out = Math.abs(r-other.r);
		out += Math.abs(g-other.g);
		out += Math.abs(b-other.b);
		return out;
	}
}
