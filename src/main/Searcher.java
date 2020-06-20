package main;

import java.util.ArrayList;

import libraries.TST;
import main.Main;

public class Searcher {
	public static int[][] search(ArrayList<String> keywords) {
		int[][] list = new int[Main.allTSTs.size()][2];
		TST<Integer> tst = new TST<Integer>();
		for (int i = 0; i < Main.allTSTs.size(); i++) {
			tst = Main.allTSTs.get(i);
			list[i][0] = 0;
			list[i][1] = i;
			for (String w : keywords) {
				if (tst.contains(w)) {
					list[i][0] += tst.get(w);
				}
			}
		}
		return list;
	}
}