package me.hafizdwp.kade_submission_5.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import me.hafizdwp.kade_submission_5.R

/**
 * @author hafizdwp
 * 29/10/2019
 **/

@Parcelize
data class LeagueData(
        var imgRes: Int,
        var id: Int,
        var name: String,
        var description: String
): Parcelable {
    companion object {
        fun getAll() = data
        private val data = listOf(
                LeagueData(R.drawable._english_premier_league, 4328, "English Premier League", """The Premier League (often referred to as the English Premier League (EPL) outside England), is the top level of the English football league system. Contested by 20 clubs, it operates on a system of promotion and relegation with the English Football League (EFL).

The Premier League is a corporation in which the member clubs act as shareholders. Seasons run from August to May with each team playing 38 matches (playing each other home and away). Most games are played on Saturday and Sunday afternoons. The Premier League has featured 47 English and two Welsh clubs since its inception, making it a cross-border league.

The competition was formed as the FA Premier League on 20 February 1992 following the decision of clubs in the Football League First Division to break away from the Football League, founded in 1888, and take advantage of a lucrative television rights deal. The deal was worth £1 billion a year domestically as of 2013–14, with BSkyB and BT Group securing the domestic rights to broadcast 116 and 38 games respectively. The league generates €2.2 billion per year in domestic and international television rights. In 2014–15, teams were apportioned revenues of £1.6 billion, rising sharply to £2.4 billion in 2016–17.

The Premier League is the most-watched sports league in the world, broadcast in 212 territories to 643 million homes and a potential TV audience of 4.7 billion people. In the 2014–15 season, the average Premier League match attendance exceeded 36,000, second highest of any professional football league behind the Bundesliga's 43,500. Most stadium occupancies are near capacity. The Premier League ranks second in the UEFA coefficients of leagues based on performances in European competitions over the past five seasons, as of 2018.

Forty-nine clubs have competed since the inception of the Premier League in 1992. Six of them have won the title: Manchester United (13), Chelsea (5), Arsenal (3), Manchester City (3), Blackburn Rovers (1), and Leicester City (1). Following the 2003–04 season, Arsenal acquired the nickname "The Invincibles" as they became, and still remain, the only club to complete a Premier League campaign without losing a single game. The record of most points in a season is 100 by Manchester City in 2017–18."""),

                LeagueData(R.drawable._french_ligue_1, 4334, "French Ligue 1", """Ligue 1 (French pronunciation: ​; League 1, formerly known as Division 1), is the French professional league for association football clubs. It is the country's primary football competition and serves as the top division of the French football league system. Ligue 1 is one of two divisions making up the Ligue de Football Professionnel, the other being Ligue 2. Contested by 20 clubs, it operates on a system of promotion and relegation with Ligue 2. Seasons run from August to May, with teams playing 38 games each totaling 380 games in the season. Most games are played on Saturdays and Sundays, with a few games played during weekday evenings. Play is regularly suspended the last weekend before Christmas for two weeks before returning in the second week of January. Ligue 1 is one of the top national leagues, currently ranked sixth in Europe behind the Spanish La Liga, English Premier League, the German Bundesliga, the Portuguese Primeira Liga and the Italian Serie A.

Ligue 1 was inaugurated on 11 September 1932 under the name National before switching to Division 1 after a year of existence. The name lasted until 2002 before switching to its current name. The current champions are Paris Saint-Germain, who won the 4th title of their history in the 2013–14 season.

Ligue 1 is generally regarded as competently run, with good planning of fixtures, complete and consistently enforced rules, timely resolution of issues, and adequate escalation procedures of judicial disputes to national or international institutions. The league has faced three significant corruption scandals in its history (Antibes in 1933, Red Star in the 1950s, and Marseille in 1993) and has preserved its reputation every time through swift and appropriately severe punishment of the guilty parties."""),

                LeagueData(R.drawable._german_bundesliga, 4331, "German Bundesliga", """Major League Soccer (MLS) is a professional soccer league representing the sport's highest level in both the United States and Canada. MLS constitutes one of the major professional sports leagues of the United States and Canada. The league is composed of 20 teams—17 in the U.S. and 3 in Canada. The MLS regular season runs from March to October, with each team playing 34 games; the team with the best record is awarded the Supporters' Shield. The post season includes twelve teams competing in the MLS Cup Playoffs through November and December, culminating in the championship game, the MLS Cup. MLS teams also play in other competitions against teams from other divisions and countries, such as the U.S. Open Cup, the Canadian Championship, and the CONCACAF Champions League. MLS is sanctioned by the United States Soccer Federation (U.S. Soccer).

Major League Soccer was founded in 1993 as part of the United States' successful bid to host the 1994 FIFA World Cup. The first season took place in 1996 with ten teams. MLS experienced financial and operational struggles in its first few years: The league lost millions of dollars, teams played in mostly empty American football stadiums, and two teams folded in 2002. Since then, MLS has expanded to 20 teams, owners built soccer-specific stadiums, average attendance at MLS matches exceeds that of the National Basketball Association (NBA) and the National Hockey League (NHL), MLS secured national TV contracts, and the league is now profitable.

Instead of operating as an association of independently owned teams, MLS is a single entity in which each team is owned and controlled by the league's investors. The investor-operators control their teams as owners control teams in other leagues, and are commonly (but inaccurately) referred to as the team's owners. The league's closed membership makes it one of the world's few soccer leagues that does not use promotion and relegation, which is uncommon in North America. MLS headquarters are in New York City."""),

                LeagueData(R.drawable._portugeuese_premiera_liga, 4344, "Portugeuese Premiera Liga", """The Primeira Liga (First League; Portuguese pronunciation: ), formerly called Primeira Divisão), is the top professional association football division of the Portuguese football league system.

The Primeira Liga is contested by 18 clubs, but only five of them have won the title. Founded in 1934, the league is in its 81st edition (counting four experimental leagues in the 1930s). It has been dominated by the "Big Three": Benfica, Porto and Sporting CP. These three clubs have won a total of 78 titles, with 
Belenenses and Boavista winning the other two."""),

                LeagueData(R.drawable._australian_a_league, 4356, "Australian A League", """The A-League is a professional men's soccer league, run by Football Federation Australia (FFA). At the top of the Australian league system, it is the country's primary competition for the sport. The A-League was established in 2004 as a successor to the National Soccer League (NSL) and competition commenced in August 2005. The league is currently contested by ten teams; nine based in Australia and one based in New Zealand. It is known as the Hyundai A-League (HAL) through a sponsorship arrangement with the Hyundai Motor Company.

Seasons run from October to May and include a 27-round regular season and an end-of-season finals series playoff tournament involving the highest-placed teams, culminating in a grand final match. The winner of the regular season tournament is dubbed 'premier' and the winner of the grand final is 'champion'. This differs from the other major football codes in Australia, where 'premier' refers to the winner of the grand final and the winner of the regular season is the 'minor premier'. The A-League's non-standard terminology is reflective of the increased prestige associated with winning the regular season in association football compared to other football codes in Australia. Successful A-League clubs gain qualification into the continental competition, the Asian Football Confederation Champions League (ACL).

Since the league's inaugural season, a total of six clubs have been crowned A-League Premiers and five clubs have been crowned A-League Champions. The current premiers and champions are Brisbane Roar, who won both titles in 2013–14."""),

                LeagueData(R.drawable._scotish_premier_league, 4330, "Scotish Premier League", """The Scottish Premiership is the top division of the Scottish Professional Football League, the league competition for professional football clubs in Scotland. The Scottish Premiership was established in July 2013, after the Scottish Professional Football League was formed by a merger of the Scottish Premier League and Scottish Football League. Teams receive three points for a win and one point for a draw. No points are awarded for a loss. Teams are ranked by total points, then goal difference, and then goals scored. At the end of each season, the club with the most points is crowned league champion. If points are equal, the goal difference and then goals scored determine the winner."""),

                LeagueData(R.drawable._english_league_1, 4396, "English League 1", """Football League One (often referred to as League One for short or Sky Bet League 1) is the second-highest division of the Football League and the third tier in the English football league system.

Football League One was introduced for the 2004–05 season. It was previously known as the Football League Second Division and prior to the advent of the Premier League, the Football League Third Division.

At present (2014–15 season), Oldham Athletic hold the longest tenure in League One, last being out of the division in the 1996–97 season when they were relegated from the Championship. There are currently six former Premier League clubs competing in the League One, namely Barnsley, Bradford City, Coventry City, Oldham Athletic, Sheffield United and Swindon Town.
There are 24 clubs in Football League One. Each club plays every other club twice (once at home and once away). Three points are awarded for a win, one for a draw and zero for a loss. At the end of the season a table of the final League standings is determined, based on the following criteria in this order: points obtained, goal difference, goals scored, an aggregate of the results between two or more clubs (ranked using the previous three criteria) and, finally, a series of one or more play-off matches.

At the end of each season the top two clubs, together with the winner of the play-offs between the clubs which finished in 3rd–6th position, are promoted to Football League Championship and are replaced by the three clubs that finished at the bottom of that division.

Similarly, the four clubs that finished at the bottom of Football League One are relegated to Football League Two and are replaced by the top three clubs and the club that won the 4th–7th place play-offs in that division.
Sky Sports currently show live League One matches with highlights shown on BBC One on their programme called The Football League Show, which also broadcasts highlights of Football League Championship and Football League Two matches. The show is available on the red button the following Sunday until midday and is available on iPlayer all the following week. Highlights of all games in the Football League are also available to view separately on the Sky Sports website. In Sweden, TV4 Sport has the rights of broadcasting from the league. A couple of league matches during the season of 09/10 including play-off matches and the play-off final to the Championship were shown. In Australia, Setanta Sports Australia broadcasts live Championship matches. In the USA and surrounding countries including Cuba, some Football League Championship, Football League One and Football League Two games are shown on beIN Sport.""")

        )
    }
}

