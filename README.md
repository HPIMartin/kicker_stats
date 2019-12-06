# Statistical Tool for Recording Player Performance in Table Soccer (Kicker)

## Ubiquitous Language
| Term | Meaning |
| ------------- |-------------|
| Player | A person playing the table soccer. |
| Team | A group of players (1+) playing together. | 
| Match | Two teams playing against each other until winning condition reached. | 
| Score | The result of a single match. | 
| Party | A party contains a group of players. States are always in context of a party. | 
| Season | A finite period of time where states are calculated and updated. |
| Leader | Administrator of a party configuring the party. A party could have several leaders, but needs to have at least one. | 

## Database draft
![database draft](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/HPIMartin/kicker_stats/master/doc/database.puml)

## Open Tasks
* Record Game Result
    * 2on2
    * 1on1
* Mails for game result confirmation
* Calculate Player ELo 
* Display Player Ranking based on Elo
    * Current Elo
    * #Win
    * #Lose
* Player Details
    * Elo change over time
    * Last Matches
    * Favorite Opponent (most wins)
    * Nemesis (most loses)
    * Best team mate
    * Worst team mate
* Team generator
    * Elo based (best balanced)
    * Random
* Dashboard 
    * Most unexpected victory (biggest ELO diff)
    * Last games
    * Top 3
    * Last 3
    * Goal diff distribution
    * Expected Goal diff based on Elo diff (Correlation?)
* Parties
* Seasons
* Tournament generator
