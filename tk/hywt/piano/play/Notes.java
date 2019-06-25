package tk.hywt.piano.play;

public class Notes {
	public static int getNote(String note) {
		switch (note) {
		case "a":
			return 48;
		case "b":
			return 50;
		case "c":
			return 52;
		case "d":
			return 53;
		case "e":
			return 55;
		case "f":
			return 57;
		case "g":
			return 59;
		case "h":
			return 60;
		case "i":
			return 62;
		case "j":
			return 64;
		case "k":
			return 65;
		case "l":
			return 67;
		case "m":
			return 69;
		case "n":
			return 71;
		case "o":
			return 72;
		case "p":
			return 74;
		case "q":
			return 76;
		case "r":
			return 77;
		case "s":
			return 79;
		case "t":
			return 81;
		case "u":
			return 83;
		case "v":
			return 84;
		case "w":
			return 86;
		case "x":
			return 88;
		case "y":
			return 89;
		case "z":
			return 91;
		case "[":
			return 51;
		case "~":
			return 54;
		case ";":
			return 56;
		case "1":
			return 58;
		case "2":
			return 61;
		case "3":
			return 63;
		case "4":
			return 66;
		case "5":
			return 68;
		case "6":
			return 70;
		case "7":
			return 73;
		case "8":
			return 75;
		case "9":
			return 78;
		case "0":
			return 80;
		case "\"":
			return 82;
		case ",":
			return 85;
		case ".":
			return 87;
		case "?":
			return 90;
		}
return 0;
	}
	public static int getControl(String note) {
		switch (note) {
		case "-":
			return 1;
		case " ":
			return 2;
		case "(":
			return 3;
		case ")":
			return 4;
		}
return 0;
	}
}
