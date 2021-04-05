package tk.hywt.piano.play;

public class Notes {
	public static int getNote(String note) {
		switch (note) {
		case "q":
			return 72;
		case "w":
			return 74;
		case "e":
			return 76;
		case "r":
			return 77;
		case "t":
			return 79;
		case "y":
			return 81;
		case "u":
			return 83;

		case "a":
			return 60;
		case "s":
			return 62;
		case "d":
			return 64;
		case "f":
			return 65;
		case "g":
			return 67;
		case "h":
			return 69;
		case "j":
			return 71;

		case "z":
			return 48;
		case "x":
			return 50;
		case "c":
			return 52;
		case "v":
			return 53;
		case "b":
			return 55;
		case "n":
			return 57;
		case "m":
			return 59;
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
