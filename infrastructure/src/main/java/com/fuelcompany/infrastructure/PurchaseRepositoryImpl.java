package com.fuelcompany.infrastructure;

import com.fuelcompany.domain.aggregateModels.report.GroupByMonthItem;
import com.fuelcompany.domain.dao.IPurchaseDAO;
import com.fuelcompany.domain.entity.PurchaseEntity;
import com.fuelcompany.infrastructure.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class PurchaseRepositoryImpl implements IPurchaseDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public PurchaseEntity save(PurchaseEntity report) {
        return purchaseRepository.save(report);
    }

    @Override
    public List<GroupByMonthItem> getTotalByMonth(Long driverId) {
        StringBuilder builder = new StringBuilder("SELECT EXTRACT(YEAR FROM p.DATE) AS YEAR, EXTRACT(MONTH FROM p.DATE) AS MONTH, sum(p.PRICE) as TOTAL")
                .append(" FROM PURCHASE p ")
                .append(driverId != null ? "WHERE p.DRIVER_ID = " + driverId : "")
                .append(" GROUP BY YEAR, MONTH ")
                .append(" ORDER BY YEAR DESC , MONTH ASC");
        return jdbcTemplate.query(builder.toString(), new GroupByMonthItemMapper());
    }

    private static final class GroupByMonthItemMapper implements RowMapper<GroupByMonthItem> {
        @Override
        public GroupByMonthItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new GroupByMonthItem(
                    rs.getString("year"),
                    rs.getString("month"),
                    rs.getBigDecimal("total")
            );
        }
    }
}