package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import de.platon42.intellij.plugins.m68k.asm.AddressMode
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findMatchingInstructions
import de.platon42.intellij.plugins.m68k.asm.OP_SIZE_W
import de.platon42.intellij.plugins.m68k.psi.M68kAddressModeUtil
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findExactIsaDataAndAllowedAdrModeForInstruction

class M68kAddressRegisterWordAccessInspection : AbstractBaseM68kLocalInspectionTool() {

    companion object {
        private const val DISPLAY_NAME = "Word access to address register"

        private const val ADDR_REG_WORD_ACCESS_MSG_TEMPLATE = "Suspicious %s %s address register"
    }

    override fun getDisplayName() = DISPLAY_NAME

    override fun checkAsmInstruction(asmInstruction: M68kAsmInstruction, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val asmOp = asmInstruction.asmOp
        if (asmOp.opSize != OP_SIZE_W) return emptyArray()

        val isaDataCandidates = findMatchingInstructions(asmOp.mnemonic)
        if (isaDataCandidates.isEmpty()) return emptyArray()
        val (isaData, adrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(asmInstruction) ?: return emptyArray()

        return when (isaData.mnemonic) {
            "movea" -> arrayOf(
                manager.createProblemDescriptor(
                    asmInstruction,
                    asmInstruction,
                    ADDR_REG_WORD_ACCESS_MSG_TEMPLATE.format(isaData.mnemonic, "word write to"),
                    ProblemHighlightType.WARNING,
                    isOnTheFly
                )
            )

            "cmpa" -> arrayOf(
                manager.createProblemDescriptor(
                    asmInstruction,
                    asmInstruction,
                    ADDR_REG_WORD_ACCESS_MSG_TEMPLATE.format(isaData.mnemonic, "comparing with word-sized"),
                    ProblemHighlightType.WARNING,
                    isOnTheFly
                )
            )

            "tst" -> if (M68kAddressModeUtil.getAddressModeForType(asmInstruction.addressingModeList.getOrNull(0)) == AddressMode.ADDRESS_REGISTER_DIRECT) {
                arrayOf(
                    manager.createProblemDescriptor(
                        asmInstruction,
                        asmInstruction,
                        ADDR_REG_WORD_ACCESS_MSG_TEMPLATE.format(isaData.mnemonic, "with word-sized"),
                        ProblemHighlightType.WARNING,
                        isOnTheFly
                    )
                )
            } else {
                emptyArray()
            }

            else -> emptyArray()
        }
    }
}
