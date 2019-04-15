package jk00687_project_com1028;

public class TableInfo {

	private int gamesPlayed = 0;
	private String teamName = null;
	private int wins = 0;
	private int draws = 0;
	private int losses = 0;
	private int goalsConceded = 0;
	private int goalsScored = 0;
	private int goalDifference = 0;
	private int points = 0;

	public TableInfo(String teamName, int gamesPlayed, int wins, int draws, int losses,
			int goalsScored, int goalsConceded, int goalDifference, int points) {
		super();

		if (gamesPlayed < 0) {
			throw new IllegalArgumentException("A negative amount of games cannot be played");
		} else {
			this.gamesPlayed = gamesPlayed;
		}

		if (teamName == null) {
			throw new IllegalArgumentException("A team must have a name");
		} else {
			this.teamName = teamName;
		}

		if (wins < 0) {
			throw new IllegalArgumentException("A negative amount of wins cannot occur");
		} else {
			this.wins = wins;
		}

		if (draws < 0) {
			throw new IllegalArgumentException("A negative amomunt of draws cannot occur");
		} else {
			this.draws = draws;
		}

		if (losses < 0) {
			throw new IllegalArgumentException("A negative amount of losses cannot occur");
		} else {
			this.losses = losses;
		}

		if (goalsConceded < 0) {
			throw new IllegalArgumentException("A negative amount of goals conceded cannot occur");
		} else {
			this.goalsConceded = goalsConceded;
		}

		if (goalsScored < 0) {
			throw new IllegalArgumentException("A negative amount of goals scored cannot occur");
		} else {
			this.goalsScored = goalsScored;
		}
		
		this.goalDifference = goalDifference;
		
		if (points < 0) {
			throw new IllegalArgumentException("Points cannot be negative");
		} else {
			this.points = points;
		}
		
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getWins() {
		return wins;
	}

	public int getDraws() {
		return draws;
	}

	public int getLosses() {
		return losses;
	}

	public int getGoalsConceded() {
		return goalsConceded;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public int getGoalDifference() {
		return goalDifference;
	}

	public int getPoints() {
		return points;
	}

}
