package space.kscience.dataforge.dataframe

import org.jetbrains.kotlinx.dataframe.api.add
import org.jetbrains.kotlinx.dataframe.api.column
import org.junit.jupiter.api.Test
import space.kscience.dataforge.misc.DFExperimental
import space.kscience.tables.ColumnHeader
import space.kscience.tables.ColumnTable
import space.kscience.tables.get
import kotlin.math.pow
import kotlin.test.assertEquals

@OptIn(DFExperimental::class)
internal class DataFrameTableTest {

    @Test
    fun convertTableToDataFrame() {
        val x by ColumnHeader.typed<Double>()
        val x2 by ColumnHeader.typed<Double>()
        val y by ColumnHeader.typed<Double>()

        val table = ColumnTable<Double?>(100) {
            //filling column with double values equal to index
            x.fill { it.toDouble() }
            //virtual column filled with x^2
            columns[x2] = { it[x].pow(2) }
            //Fixed column filled with x^2 + 1
            columns[y] = x2.values.map { it?.plus(1) }
        }

        val dataFrame = table.toDataFrame()

        //println( dataFrame)

        val z by column<Double>()

        val newFrame = dataFrame.add {
            //z.from{ it[x] + it[y] + 1.0} // conflicts with JUnit
        }

        //println(newFrame)

        val newTable = newFrame.asTable()

        assertEquals(newTable.columns[x], table.columns[x])
    }
}