package jk00687_project_com1028;

public class Match {

	private String team1name = null;
	private String team2name = null;
	private int team1score = 0;
	private int team2score = 0;

	public Match(String team1name, String team2name, int team1score, int team2score) {
		super();
		if (team1name == null) {
			throw new IllegalArgumentException("Team must have a name");
		} else {
			this.team1name = team1name;
		}

		if (team2name == null) {
			throw new IllegalArgumentException("Team must have a name");
		} else {
			this.team2name = team2name;
		}

		if (team1score < 0) {
			throw new IllegalArgumentException("There cannot be a negative score");
		} else {
			this.team1score = team1score;
		}

		if (team2score < 0) {
			throw new IllegalArgumentException("There cannot be a negative score");
		} else {
			this.team2score = team2score;
		}

	}

	public String getTeam1name() {
		return team1name;
	}

	public String getTeam2name() {
		return team2name;
	}

	public int getTeam1score() {
		return team1score;
	}

	public int getTeam2score() {
		return team2score;
	}

}
