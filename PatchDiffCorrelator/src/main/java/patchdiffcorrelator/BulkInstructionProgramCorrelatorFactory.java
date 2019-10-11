/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package patchdiffcorrelator;

import ghidra.feature.vt.api.main.VTProgramCorrelator;
import ghidra.feature.vt.api.util.VTAbstractProgramCorrelatorFactory;
import ghidra.feature.vt.api.util.VTOptions;
import ghidra.framework.plugintool.ServiceProvider;
import ghidra.program.model.address.AddressSetView;
import ghidra.program.model.listing.Program;
import ghidra.util.HelpLocation;

public class BulkInstructionProgramCorrelatorFactory extends VTAbstractProgramCorrelatorFactory {

	static final String DESC = "Compares functions based on their included instructions without taking the order of the instructions into account.";
	static final String NAME = "Bulk Instructions Match";

	public static final String SIMILARITY_THRESHOLD = "Minimum similarity threshold (score)";
	public static final String SIMILARITY_THRESHOLD_DESC = "Similarity should be between 0 and 1";
	public static final double SIMILARITY_THRESHOLD_DEFAULT = 0.5;

	public static final String CONFIDENCE_THRESHOLD = "Minimum confidence threshold (score)";
	public static final String CONFIDENCE_THRESHOLD_DESC = "Confidence will be 1.0 (symbols don't match) or 10.0 (symbols match)";
	public static final double CONFIDENCE_THRESHOLD_DEFAULT = 0.0;

	public static final String SYMBOL_NAMES_MUST_MATCH = "Symbol names must match";
	public static final String SYMBOL_NAMES_MUST_MATCH_DESC = "Only match functions that have the same symbol names.";
	public static final boolean SYMBOL_NAMES_MUST_MATCH_DEFAULT = true;

	private static final String helpLocationTopic = "patchdiffcorrelator";
	protected String helpLocationAnchor = "PatchDiffCorrelator";

	@Override
	protected VTProgramCorrelator doCreateCorrelator(ServiceProvider serviceProvider,
			Program sourceProgram, AddressSetView sourceAddressSet, Program destinationProgram,
			AddressSetView destinationAddressSet, VTOptions options) {
		return new BulkProgramCorrelator(serviceProvider, sourceProgram, sourceAddressSet,
			destinationProgram, destinationAddressSet, options, NAME, InstructionFunctionBulker.INSTANCE);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESC;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public VTOptions createDefaultOptions() {
		VTOptions options = new VTOptions(NAME);
		HelpLocation help = new HelpLocation(helpLocationTopic, helpLocationAnchor);
		options.setOptionsHelpLocation(help);
		options.registerOption(SIMILARITY_THRESHOLD, SIMILARITY_THRESHOLD_DEFAULT, help, SIMILARITY_THRESHOLD_DESC);
		options.setDouble(SIMILARITY_THRESHOLD, SIMILARITY_THRESHOLD_DEFAULT);
		options.registerOption(CONFIDENCE_THRESHOLD, CONFIDENCE_THRESHOLD_DEFAULT, help, CONFIDENCE_THRESHOLD_DESC);
		options.setDouble(CONFIDENCE_THRESHOLD, CONFIDENCE_THRESHOLD_DEFAULT);
		options.registerOption(SYMBOL_NAMES_MUST_MATCH, SYMBOL_NAMES_MUST_MATCH_DEFAULT, help, SYMBOL_NAMES_MUST_MATCH_DESC);
		options.setBoolean(SYMBOL_NAMES_MUST_MATCH, SYMBOL_NAMES_MUST_MATCH_DEFAULT);
		return options;
	}


}
