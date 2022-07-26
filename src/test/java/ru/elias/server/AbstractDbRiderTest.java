package ru.elias.server;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;

@DataSet(value = "data/yml/empty.yml",
         cleanBefore = true,
         cleanAfter = true,
         executeScriptsBefore = "data/sql/reset_seq.sql")
@DBUnit(mergeDataSets = true, caseSensitiveTableNames = true)
@DBRider
public abstract class AbstractDbRiderTest extends AbstractSpringTest {
}
