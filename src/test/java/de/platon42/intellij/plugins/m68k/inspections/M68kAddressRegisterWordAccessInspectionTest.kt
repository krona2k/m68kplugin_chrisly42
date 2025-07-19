package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.MyFixture
import org.junit.jupiter.api.Test

internal class M68kAddressRegisterWordAccessInspectionTest : AbstractInspectionTest() {

    @Test
    internal fun shows_warning_on_movea_word_sized(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kAddressRegisterWordAccessInspection::class.java)
        myFixture.configureByText("wordaccess.asm", " move.w d0,a0")
        assertHighlightings(myFixture, 1, "Suspicious movea word write to address register")
    }

    @Test
    internal fun shows_warning_on_cmpa_word_sized(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kAddressRegisterWordAccessInspection::class.java)
        myFixture.configureByText("wordaccess.asm", " cmp.w d0,a0")
        assertHighlightings(myFixture, 1, "Suspicious cmpa comparing with word-sized address register")
    }

    @Test
    internal fun shows_warning_on_tst_with_word_sized_address_register(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kAddressRegisterWordAccessInspection::class.java)
        myFixture.configureByText("wordaccess.asm", " tst.w a0")
        assertHighlightings(myFixture, 1, "Suspicious tst with word-sized address register")
    }
}
