package com.fuelcompany.infrastructure;

import com.fuelcompany.domain.aggregateModels.report.entity.RecordByMonthEntity;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;
import com.fuelcompany.domain.dao.IPurchaseDAO;
import com.fuelcompany.domain.dao.PurchaseEntity;
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
    public List<TotalByMonthEntity> getAmountByMonths(Long driverId) {
        String builder =
                "SELECT EXTRACT(YEAR FROM p.DATE) AS year, EXTRACT(MONTH FROM p.DATE) AS month, SUM(p.VOLUME * p.PRICE) AS total" +
                " FROM PURCHASE p " +
                (driverId != null ? "WHERE p.DRIVER_ID = " + driverId : "") +
                " GROUP BY year, month " +
                " ORDER BY year DESC, month ASC";
        return jdbcTemplate.query(builder, new TotalByMonthMapper());
    }

    @Override
    public List<RecordByMonthEntity> getReportByMonth(int monthsNumber, Long driverId, Integer year) {
        String sql =
                "SELECT p.FUEL_TYPE AS type," +
                " p.VOLUME AS volume," +
                " p.DATE AS purchaseDate," +
                " p.PRICE AS price," +
                " p.DRIVER_ID AS driverId," +
                " EXTRACT(YEAR FROM p.DATE) AS year," +
                " EXTRACT(MONTH FROM p.DATE) AS month," +
                " p.VOLUME * p.PRICE AS totalPrice"+
                " FROM PURCHASE p" +
                " WHERE EXTRACT(MONTH FROM p.DATE) = " + monthsNumber +
                (year != null ? " AND EXTRACT(YEAR FROM p.DATE) = " + year : "") +
                (driverId != null ? " AND p.DRIVER_ID = " + driverId : "") +
                " ORDER BY year DESC, month ASC";
        return jdbcTemplate.query(sql, new MonthMapper());
    }

    private static final class TotalByMonthMapper implements RowMapper<TotalByMonthEntity> {
        @Override
        public TotalByMonthEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TotalByMonthEntity(
                    rs.getInt("year"),
                    rs.getString("month"),
                    rs.getBigDecimal("total")
            );
        }
    }

    private static final class MonthMapper implements RowMapper<RecordByMonthEntity> {
        @Override
        public RecordByMonthEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new RecordByMonthEntity(
                    rs.getInt("year"),
                    rs.getString("type"),
                    rs.getBigDecimal("volume"),
                    rs.getDate("date").toLocalDate(),
                    rs.getBigDecimal("price"),
                    rs.getBigDecimal("totalPrice"),
                    rs.getInt("driverId")
            );
        }
    }
}