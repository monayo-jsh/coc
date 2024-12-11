package open.api.coc.clans.clean.infrastructure.event.persistence.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaEventTeamLegendMemberHistoryRepository extends JpaRepository<EventTeamLegendMemberHistoryEntity, Long> {

    @Query(
        nativeQuery = true,
        value = " SELECT  "
              + "    ranking_date as rankingDate,  "
              + "    team_legend_id as teamId,  "
              + "    etl.name as teamName, "
              + "    total_trophies as trophies, "
              + "    RANK() OVER (PARTITION BY ranking_date ORDER BY total_trophies DESC) as dailyRank "
              + "FROM ( "
              + "    SELECT  "
              + "        TO_CHAR(created_at, 'yyyy-MM-dd') AS ranking_date,  "
              + "        team_legend_id,  "
              + "        SUM(trophies) AS total_trophies "
              + "    FROM  "
              + "        TB_EVENT_TEAM_LEGEND_MEMBER_HISTORY "
              + "    GROUP BY  "
              + "        TO_CHAR(created_at, 'yyyy-MM-dd'), team_legend_id "
              + ") AS daily_summary "
              + "JOIN TB_EVENT_TEAM_LEGEND etl on etl.id = daily_summary.team_legend_id "
              + "ORDER BY  "
              + "    ranking_date, dailyRank"
    )
    List<Object[]> findAllTeamLegendDailyRankingsByIds(List<Long> teamIds);

}
