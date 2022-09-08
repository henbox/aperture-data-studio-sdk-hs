package com.experian.datastudio.fibonaccisequence;

import com.experian.datastudio.sdk.api.CustomTypeMetadata;
import com.experian.datastudio.sdk.api.CustomTypeMetadataBuilder;
import com.experian.datastudio.sdk.api.step.CustomStepDefinition;
import com.experian.datastudio.sdk.api.step.configuration.*;
import com.experian.datastudio.sdk.api.step.processor.OutputColumnManager;
import com.experian.datastudio.sdk.api.step.processor.StepProcessor;
import com.experian.datastudio.sdk.api.step.processor.StepProcessorBuilder;
import com.experian.datastudio.sdk.api.step.processor.StepProcessorContext;

import java.util.function.Function;

public class FibonacciSequence  implements CustomStepDefinition {

    private static final String INPUT_0 = "input0";
    private static final String OUTPUT_0 = "output0";
    private static final String SEQUENCE_LENGTH = "lengthOfFibonacciSequence";
    private static final int DEFAULT_SEQUENCE_LENGTH = 10;
    private static final String FIBONACCI_COLUMN = "Fibonacci Number";

    @Override
    public StepConfiguration createConfiguration(StepConfigurationBuilder configurationBuilder) {
        return configurationBuilder
                .withNodes(stepNodeBuilder -> stepNodeBuilder
                        .addInputNode(inputNodeBuilder -> inputNodeBuilder
                                .withId(INPUT_0)
                                .withLabelDisplayed(false)
                                .withType(NodeType.PROCESS)
                                .withIsRequired(false)
                                .build())
                        .addOutputNode(OUTPUT_0)
                        .build())
                .withStepProperties(stepPropertiesBuilder -> stepPropertiesBuilder
                        .addStepProperty(stepPropertyBuilder -> stepPropertyBuilder
                                .asNumber(SEQUENCE_LENGTH)
                                .withIsRequired(true)
                                .withDefaultValue(DEFAULT_SEQUENCE_LENGTH)
                                .withLabelSupplier(context -> "Sequence length")
                                .build())
                        .build())
                .withIsCompleteHandler(context -> true)
                .withOutputLayouts(outputLayoutBuilder -> outputLayoutBuilder
                        .forOutputNode(OUTPUT_0, getOutputColumns())
                        .build())
                .withIcon(StepIcon.FUNCTIONS)
                .build();
    }

    private static Function<OutputLayoutBuilder.OutputColumnBuilder, OutputLayout> getOutputColumns() {
        return outputColumnBuilder -> outputColumnBuilder
                .addColumn(FIBONACCI_COLUMN)
                .build();
    }

    @Override
    public StepProcessor createProcessor(StepProcessorBuilder processorBuilder) {
        return processorBuilder
                // execute
                .forOutputNode(OUTPUT_0, (StepProcessorContext stepProcessorContext,
                                          OutputColumnManager outputColumnManager) -> {
                    final Number numberOfRows = (Number)stepProcessorContext.getStepPropertyValue(SEQUENCE_LENGTH).get();

                    FibonacciSequenceGenerator fibonacciSequenceGenerator = new FibonacciSequenceGenerator(numberOfRows.intValue());

                    outputColumnManager
                            .onValue(FIBONACCI_COLUMN, fibonacciSequenceGenerator::getFibonacciNumber);

                    return numberOfRows.longValue();
                })
                .build();
    }

    @Override
    public CustomTypeMetadata createMetadata(CustomTypeMetadataBuilder metadataBuilder) {
        return metadataBuilder
                .withName("Fibonacci Generator")
                .withDescription("Generates the Fibonacci sequence up to n")
                .withMajorVersion(1)
                .withMinorVersion(0)
                .withPatchVersion(0)
                .withDeveloper("Experian")
                .withLicense("Apache 2.0")
                .build();
    }
}
