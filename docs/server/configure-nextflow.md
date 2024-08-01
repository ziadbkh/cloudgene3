# Configure Nextflow

This page helps you configure Nextflow and describes all parameters.

Cloudgene automatically creates different `nextflow.config` files and adds them sequentially to the execution. The first is the `nextflow.global.config` file, and the second is the `nextflow.app.config` file. This combination allows you to share configurations among all applications or overwrite them with specific settings or default parameters.

## Global Nextflow Settings

To configure your Nextflow settings, you can access the configuration file in **Admin/Settings/Nextflow** and fine-tune it according to your specific needs. Additionally, Nextflow offers robust support for environment variables, allowing you to easily integrate Cloudgene variables into your configuration. The `nextflow.config` file applies to all applications.

| Variable                    | Description                                      |
|-----------------------------|--------------------------------------------------|
| `${CLOUDGENE_SERVICE_NAME}` | The name of the service.                         |
| `${CLOUDGENE_SERVICE_URL}`  | The full URL of the service.                     |
| `${CLOUDGENE_CONTACT_EMAIL}`| The contact email of the service administrator.  |
| `${CLOUDGENE_CONTACT_NAME}` | The contact name of the service administrator.   |
| `${CLOUDGENE_SMTP_HOST}`    | The SMTP host for email services.                |
| `${CLOUDGENE_SMTP_PORT}`    | The SMTP port for email services.                |
| `${CLOUDGENE_SMTP_USER}`    | The SMTP user for email services.                |
| `${CLOUDGENE_SMTP_PASSWORD}`| The SMTP password for email services.            |
| `${CLOUDGENE_SMTP_NAME}`    | The SMTP name for email services.                |
| `${CLOUDGENE_SMTP_SENDER}`  | The SMTP sender name for email services.         |
| `${CLOUDGENE_WORKSPACE_TYPE}`| The type of external workspace.                 |
| `${CLOUDGENE_WORKSPACE_HOME}`| The location of the external workspace.         |

### Examples

**Configure email:**

```groovy
// Mail configuration
params.send_mail = true
mail {
    from = "${CLOUDGENE_SMTP_NAME}"
    smtp.host = "${CLOUDGENE_SMTP_HOST}"
    smtp.port = "${CLOUDGENE_SMTP_PORT}"
}
```

## Application-Specific Nextflow Settings

For each application, custom Nextflow configurations can be made. File configurations can be changed in **Admin/Applications** by clicking on the cog icon.

For each application, a custom **profile** can be set. The profile to be used for pipeline execution (e.g., a nf-core pipeline supports 'docker', 'conda', 'singularity', or 'AWSBATCH').

For each application, a working **directory** can be set. Cloudgene creates a temporary work directory for each job and deletes it after execution. You can change this or set it to an S3 path if you use AWS Batch.

In addition to the variables listed in "Global Nextflow Settings", the following are available:

| Variable                    | Description                                      |
|-----------------------------|--------------------------------------------------|
| `${CLOUDGENE_APP_ID}`       | The ID of the application.                       |
| `${CLOUDGENE_APP_VERSION}`  | The version of the application.                  |
| `${CLOUDGENE_APP_LOCATION}` | The local folder path of the application.        |
| `${CLOUDGENE_JOB_ID}`       | The ID of the job.                               |
| `${CLOUDGENE_JOB_NAME}`     | The name of the job.                             |
| `${CLOUDGENE_USER_NAME}`    | The username of the user.                        |
| `${CLOUDGENE_USER_EMAIL}`   | The email address of the user.                   |
| `${CLOUDGENE_USER_FULL_NAME}`| The full name of the user.                      |

