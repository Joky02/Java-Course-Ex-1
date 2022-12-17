package joky02.bobing.schema;

import java.util.Arrays;
import java.util.OptionalInt;

public class Prize {
	public int level;
	public int count;

	public Prize(int level, int count) {
		this.level = level;
		this.count = count;
	}

	public static boolean isChampion(int level) {
		return level >= 6;
	}

	public static String toString(int level) {

		if (level == 0) return "无";
		else if (level == 1) return "一秀";
		else if (level == 2) return "二举";
		else if (level == 3) return "三红";
		else if (level == 4) return "四进";
		else if (level == 5) return "对堂";
		else if (level == 6) return "状元";
		else if (level == 7) return "五子登科";
		else if (level == 8) return "状元插金花";
		return "六杯红";
	}

	/*
	（1）有1个“四点”，得“一秀”饼； level 1
	（2）有2个“四点”，得“二举”饼； level 2
	（3）有3个“四点”，得“三红”饼； level 3
	（4）有4个相同点数（除四点外），得“四进”饼； level 4
	（5）若骰子点数分别为1-6顺序排列，得“对堂”饼； level 5
	（6）玩家得“状元”饼的情况有几种，其等级按从小到大排列如下：
	a)有4个“四点”，得“状元”； level 6
	b)有5个相同点数的，得“五子登科”； level 7
	c)有4个“四点”，加上2个“一点”，得“状元插金花”； level 8
	（7）特殊情况： 若玩家掷出6个“四点”得“六杯红”，则直接夺得所有奖品。 level 9
	else level 0
	 */

	public static int fromDices(Dice[] dices) {
		if (Arrays.stream(dices).allMatch(d -> d.pip == 4)) return 9;
		if (Arrays.stream(dices).filter(d -> d.pip == 4).count() == 4 && Arrays.stream(dices).filter(d -> d.pip == 1).count() == 2) return 8;
		int[] cnt = new int[6];
		Arrays.stream(dices).forEach(d -> cnt[d.pip - 1]++);
		if (Arrays.stream(cnt).max().equals(OptionalInt.of(5))) return 7;
		if (Arrays.stream(dices).filter(d -> d.pip == 4).count() == 4) return 6;
		if (Arrays.stream(cnt).allMatch(c -> c == 1)) return 5;
		if (Arrays.stream(cnt).max().equals(OptionalInt.of(4))) return 4;
		if (Arrays.stream(dices).filter(d -> d.pip == 4).count() == 3) return 3;
		if (Arrays.stream(dices).filter(d -> d.pip == 4).count() == 2) return 2;
		if (Arrays.stream(dices).filter(d -> d.pip == 4).count() == 1) return 1;
		return 0;
	}
}
