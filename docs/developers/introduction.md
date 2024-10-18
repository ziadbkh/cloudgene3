# Introduction

This guide will walk you through creating a Cloudgene YAML file that acts as a bridge between the Nextflow pipeline and Cloudgene. We'll create a Cloudgene YAML file, define workflow steps, inputs, and outputs, and demonstrate how to set default parameters.

## Prerequisites

Before you begin, ensure you have the following:

- Cloudgene installed
- Nextflow installed
- Basic understanding of YAML syntax

## Creating the Cloudgene YAML File

The Cloudgene YAML file defines the link between the Nextflow pipeline and Cloudgene. It contains metadata about the pipeline, the workflow steps, inputs, and outputs.

### Header Section

The header section includes basic information about the pipeline:

```yaml
id: fetch-ngs
name: FetchNGS
description: Pipeline to fetch metadata and raw FastQ files from public databases
version: 1.12.0
website: https://github.com/nf-core/fetchngs
author: Harshil Patel, Moritz E. Beber and Jose Espinosa-Carrasco
logo: https://raw.githubusercontent.com/nf-core/fetchngs/master/docs/images/nf-core-fetchngs_logo_light.png
```

### Workflow Section

The workflow section defines the steps, inputs, and outputs.

#### Defining the Workflow Object

In the workflow object, we define a step that executes the `nf-core/fetchngs` pipeline at version 1.12.0:

```yaml
workflow:
  steps:
    - name: Fetch NGS
      script: nf-core/fetchngs
      revision: 1.12.0
```

#### Defining Inputs and Outputs

The pipeline has one input and one output. We define the corresponding variables and their types.

**Example with File Input:**

```yaml
inputs:
  - id: input
    description: ID File
    type: local_file

outputs:
  - id: outdir
    description: Output
    type: local-folder
```

Cloudgene automatically creates a user interface with input parameters. Upon submission, it generates the outputs (folders or files). All inputs and outputs are automatically added to the `params.json` file, which Cloudgene uses to execute the Nextflow workflow.

### Extended Example with Textarea Input

We can extend the configuration to allow users to enter a list of IDs in a textarea. Cloudgene writes this content to a file.

**Example with Textarea Input:**

```yaml
workflow:
  steps:
    - name: Fetch NGS
      script: nf-core/fetchngs
      revision: 1.12.0

  inputs:
    - id: input
      description: IDs
      type: textarea
      writeFile: "ids.csv"

  outputs:
    - id: outdir
      description: Output
      type: local-folder
```

### Setting Default Parameters

We can also set default parameters without requiring user inputs.

**Example with Default Parameters:**

```yaml
workflow:
  steps:
    - name: Fetch NGS
      script: nf-core/fetchngs
      revision: 1.12.0
      params:
        monochrome_logs: false
```

## Connecting Pipelines

Cloudgene supports the connection of multiple pipelines by allowing you to define workflows that consist of multiple steps. Each step can execute a separate pipeline, and the output of one pipeline can be passed as the input to the next.

In this example, Cloudgene connects two Nextflow pipelines (nf-core/fetchngs and nf-core/taxprofiler) into a single workflow, making it easy to fetch data and then run a taxonomic profiler.

```yaml
workflow:
  steps:
    - name: Fetch Data
      script: nf-core/fetchngs
      revision: 1.12.0
      stdout: true
      params:
        input: "${input_ids}"

    - name: Run taxprofiler
      script: nf-core/taxprofiler
      revision: 1.1.8
      stdout: true
      params:
        input: "${outdir}/samplesheet/samplesheet.csv"
        databases: "https://raw.githubusercontent.com/nf-core/test-datasets/taxprofiler/database_full_v1.2.csv"
        multiqc_title: "${CLOUDGENE_JOB_NAME}"

  inputs:
    - id: input_ids
      description: IDs
      type: textarea
      value: "SRR12696236"
      writeFile: "ids.csv"
      serialize: false

  outputs:
    - id: outdir
      description: Output
      type: local_folder
```

## Local Nextflow Script

You can also place the `cloudgene.yaml` file directly into your pipeline directory and set the `script` property to the Nextflow script (e.g., `main.nf`). In this case, if you install the application via GitHub, Cloudgene automatically downloads the pipeline and executes the script.

```yaml
workflow:
  steps:
    - name: Execute Pipeline
      script: main.nf
```

## Complete Example

Here’s a complete example combining all the sections:

```yaml
id: fetch-ngs
name: FetchNGS
description: Pipeline to fetch metadata and raw FastQ files from public databases
version: 1.12.0
website: https://github.com/nf-core/fetchngs
author: Harshil Patel, Moritz E. Beber and Jose Espinosa-Carrasco
logo: https://raw.githubusercontent.com/nf-core/fetchngs/master/docs/images/nf-core-fetchngs_logo_light.png

workflow:
  steps:
    - name: Fetch NGS
      script: nf-core/fetchngs
      revision: 1.12.0
      params:
        monochrome_logs: false

  inputs:
    - id: input
      description: IDs
      type: textarea
      writeFile: "ids.csv"

  outputs:
    - id: outdir
      description: Output
      type: local-folder
```

## Conclusion

You have now created a Cloudgene YAML file that defines a Nextflow pipeline workflow with inputs, outputs, and default parameters. This configuration allows Cloudgene to generate a user interface, handle inputs and outputs, and execute the Nextflow workflow seamlessly.

For more information, refer to the [Cloudgene documentation](https://cloudgene.readthedocs.io/) and the [nf-core/fetchngs repository](https://github.com/nf-core/fetchngs).