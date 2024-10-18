# Manage Permissions

This page helps you manage permissions at the application and user level.

## Applications

When you install a new application, the permission group of the new application is automatically set to the group **User**.

Open the **Admin Panel** and click on the **Applications** tab to see all installed applications.

![Applications Permission Button](../../images/screenshots/apps-permission-button.png)

Click on the **User** button, and a new dialog will appear that lists all available groups. You can assign multiple groups per application. For example, you can change the group from **User** to **Admin** to restrict access to this application only to users with administration privileges.

![Application Permissions Admin](../../images/screenshots/app-permissions-admin.png)

## Creating New Groups

If you want to create a new group, please select **New Group...** and enter a name for the new group (e.g., **beta-tester**).

![Create New Group](../../images/screenshots/app-permissions.png)

By clicking on **OK**, the permission group of the selected application is updated. Now, only users who are members of **beta-tester** can use this application.

## Users

When a new user is registered, the user is a member of the group **User**. This user has access to all applications with the permission group **User**. A user can be part of one or more groups. Users in the group **Admin** have access to **all** applications.

Open the **Admin Panel** and click on the **Users** tab to see all users.

![Users List](../../images/screenshots/users.png)

By clicking on the **User** button, a new dialog will appear that lists all available groups.

![User Permissions](../../images/screenshots/user-permissions.png)

The newly created group **beta-tester** from the example above is now listed. Click on the checkbox to add the selected user to this new group and close the dialog by clicking on **OK**.

The user is now a member of **User** and **beta-tester** and has access to the **Hello Cloudgene** app.