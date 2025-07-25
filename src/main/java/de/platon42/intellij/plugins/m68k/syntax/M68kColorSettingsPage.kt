package de.platon42.intellij.plugins.m68k.syntax

import com.intellij.codeHighlighting.RainbowHighlighter
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.RainbowColorSettingsPage
import de.platon42.intellij.plugins.m68k.M68kIcons.FILE
import de.platon42.intellij.plugins.m68k.MC68000Language
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.AREG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.BAD_CHARACTER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.COLON
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.COMMENT
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_PREPROCESSOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_WIDTH_BS
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_WIDTH_L
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_WIDTH_W
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DREG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.GLOBAL_LABEL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.LOCAL_LABEL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MACRO_CALL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MACRO_LINE
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MACRO_NAME_DEF
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MACRO_PREPROCESSOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MNEMONIC
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.NUMBER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.OTHER_PREPROCESSOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.PROGRAM_COUNTER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SEPARATOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SPECIAL_REG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.STACK_POINTER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.STRING
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SYMBOL_DEF
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SYMBOL_REF
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class M68kColorSettingsPage : RainbowColorSettingsPage {

    override fun getLanguage() = MC68000Language.INSTANCE

    override fun getIcon(): Icon {
        return FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return M68kSyntaxHighlighter(null)
    }

    @NonNls
    override fun getDemoText(): String {
        return """; This is an example assembly language program
PIC_HEIGHT = 256

* Semantic highlighting is available for registers and local labels

        include "../includes/hardware/custom.i"

BLTHOGON MACRO                  ; macro definition
        move.w	#DMAF_SETCLR|DMAF_BLITHOG,dmacon(a5) ; hog!
        ENDM

demo_init                       ; global label
        tst.w   d1
        beq.s   .skip
        PUSHM   d0-d7/a0-a6     ; this is a macro call
        lea     hello(pc),a1
        lea     pd_ModViewTable(a4,d1.w),a0
        moveq.l #0,d0
        move.w  #PIC_HEIGHT-1,d7
.loop   move.l  d0,(a0)+        ; local label
        dbra    d7,.loop
        POPM
.skip   rts

irq:    move.l  a0,-(sp)
        move    usp,a0
        move.l  a0,$400.w
        move.l  (sp)+,a0
        rte

hello:  dc.b   'Hello World!',10,0
        even
        dc.w    *-hello         ; length of string
"""
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> {
        return RainbowHighlighter.createRainbowHLM()
    }

    override fun isRainbowType(type: TextAttributesKey) = RAINBOW_TYPES.contains(type)

    override fun getAttributeDescriptors() = DESCRIPTORS

    override fun getColorDescriptors() = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName() = "M68k Assembly"

    companion object {
        private val RAINBOW_TYPES = setOf(AREG, DREG, LOCAL_LABEL)

        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Global labels", GLOBAL_LABEL),
            AttributesDescriptor("Local labels", LOCAL_LABEL),
            AttributesDescriptor("Comma (separator)", SEPARATOR),
            AttributesDescriptor("Colon", COLON),
            AttributesDescriptor("Symbol definition", SYMBOL_DEF),
            AttributesDescriptor("Symbol reference", SYMBOL_REF),
            AttributesDescriptor("Assembly mnemonic", MNEMONIC),
            AttributesDescriptor("Macro name definition", MACRO_NAME_DEF),
            AttributesDescriptor("Macro preprocessor directives", MACRO_PREPROCESSOR),
            AttributesDescriptor("Macro line", MACRO_LINE),
            AttributesDescriptor("Macro invocation", MACRO_CALL),
            AttributesDescriptor("Data width: Byte/short", DATA_WIDTH_BS),
            AttributesDescriptor("Data width: Word", DATA_WIDTH_W),
            AttributesDescriptor("Data width: Long", DATA_WIDTH_L),
            AttributesDescriptor("Data preprocessor directives", DATA_PREPROCESSOR),
            AttributesDescriptor("Other preprocessor directives", OTHER_PREPROCESSOR),
            AttributesDescriptor("Strings", STRING),
            AttributesDescriptor("Numbers", NUMBER),
            AttributesDescriptor("Registers: Address registers", AREG),
            AttributesDescriptor("Registers: Stack pointer", STACK_POINTER),
            AttributesDescriptor("Registers: Data registers", DREG),
            AttributesDescriptor("Registers: Program counter", PROGRAM_COUNTER),
            AttributesDescriptor("Registers: Special registers", SPECIAL_REG),
            AttributesDescriptor("Comments", COMMENT),
            AttributesDescriptor("Bad characters", BAD_CHARACTER)
        )
    }
}