# AXEL - Software Development Requirement & Feature Management System

## PART I: Purpose

The primary objective of AXEL is to streamline the software development lifecycle (SDLC) by providing a unified platform for requirement management, resource scheduling, and process automation.

### Key Goals

- **Improve Efficiency**: Eliminate communication gaps between Product Owners and Developers by centralizing specifications
- **Traceability**: Ensure every line of code or task can be traced back to an original business requirement
- **Process Compliance**: Enforce strict audit trails and approval workflows using a BPMN engine (Flowable) to meet quality standards
- **Resource Optimization**: Visualize developer availability to prevent burnout and ensure timely delivery

## PART II: Functional Design

This section defines the core functional capabilities of the system modules.

### 1. User Module
- **Profile Management**: Stores user skills, seniority levels, and contact info
- **Department/Team Logic**: Users belong to functional groups (e.g., "Backend Team A") for collective assignment

### 2. Authorization Module (RBAC)
- **Role-Based Access**: Standard roles include System Admin, Program Manager, Product Owner, Tech Lead, Developer, Auditor
- **Granular Permissions**: Specific actions (e.g., APPROVE_REQUIREMENT, ASSIGN_MISSION) are gated by roles

### 3. Requirement Management Module (Core)
- **Lifecycle Management**: Tracks a requirement from Draft -> Review -> Development -> Audit -> Done
- **Versioning**: Maintains a history of changes to requirement specs
- **Traceability**: Links requirements to specific "Missions" (Dev tasks)

### 4. Program Management Module
- **Portfolio Hierarchy**: Groups requirements into Epics and Programs
- **Milestone Tracking**: Sets high-level deadlines that cascade down to individual requirements

### 5. Task Event Process Management Module (Workflow)
- **Engine**: Powered by Flowable
- **State Machine**: Automates transitions. For example, when a developer clicks "Submit," the engine automatically moves the state to "Audit" and notifies the Manager
- **Audit Trail**: Logs every state change for compliance reporting

### 6. Scheduling Module
- **Resource Grid**: Visualizes developer workloads over time
- **Conflict Detection**: Alerts managers if a developer is double-booked
- **Auto-Suggestion**: Recommends available developers based on skills matching the requirement tags

### 7. Report Module
- **Automated Generation**: Creates PDF/CSV reports for sprint velocity, individual performance, and program health
- **Dashboards**: Real-time visual metrics

## PART III: Front End Website Layout (Vue 3)

**Technology Stack**: Vue 3 (Composition API), Vite, Pinia, Tailwind CSS

### 1. Main Page (Dashboard)
- **Layout**: Customizable grid layout
- **Widgets**:
  - System Stats: Total Active Requirements, On-Time Rate
  - My Tasks: Quick list of missions assigned to the current user
  - Team Load: Bar chart showing Department capacity
  - Watchlist: Pinned requirements the user is interested in

### 2. Requirement Management Page
- **List View**: Data grid with filters (Status, Priority, Program)
- **Create/Edit Interface**:
  - Rich Text Editor for descriptions
  - File Uploader: Drag-and-drop for specs/mockups
  - Linker: Search and attach related Personnel or Dependent Requirements
- **Action Bar**: Buttons driven by current status (e.g., "Submit for Audit", "Start Development")

### 3. Audit Page (Manager/QA View)
- **Inbox**: Queue of items awaiting approval
- **Comparison View**: "Diff" view showing what changed in the requirement since the last approval
- **Decision Controls**: "Approve" (advances workflow) or "Reject" (returns to Dev with comments)

### 4. Program Management Page
- **Kanban Board**: Move requirements between program phases
- **Gantt Chart**: Visual timeline of the program
- **Backlog Manager**: Drag-and-drop requirements from "Unassigned" to specific Programs

### 5. Scheduling Page
- **Calendar View**:
  - Y-Axis: Departments/Developers
  - X-Axis: Timeline (Days/Weeks)
- **Interaction**: Drag "Unscheduled Missions" from a sidebar onto a Developer's timeline row

### 6. Department Page (Admin)
- **Team Builder**: Add/Remove users from departments
- **Role Manager**: checkboxes to toggle specific permissions for custom roles

### 7. Personal Page
- **Settings**: Theme toggle (Dark/Light), Notification preferences (Email vs. In-App)

## PART IV: Back End System Design (Spring Boot)

**Technology Stack**: Spring Boot 3.x, Java 21, PostgreSQL, Flowable Engine

### 1. Code Modules (Maven/Gradle Modules)
- **axel-common**: Shared DTOs, Utilities, and Exception Handlers
- **axel-auth (User & Auth)**:
  - Implements Spring Security with JWT
  - Manages User and Role entities
- **axel-core (Requirement & Program)**:
  - RequirementService: CRUD and business logic
  - ProgramService: Aggregation logic
- **axel-workflow (Process Engine)**:
  - Embeds Flowable
  - ProcessDelegate: Java classes that execute when a BPMN task is triggered
  - AuditListener: Listens to workflow events to record audit logs
- **axel-schedule (Task Scheduling)**:
  - AvailabilityService: Calculates free slots for users
  - AssignmentController: Handles manual and auto-assignment logic

### 2. Database Design (PostgreSQL)
- Core Tables
- Functional Tables
- Scheduling Tables
- Workflow Tables (Flowable Defaults)

### 3. Development Architecture
- Vue3
- SpringBoot 3.5.8
- Java 21
- REST API
- PostgreSQL
- Flowable

## PART V: Feature Lifecycle Walkthrough

This section illustrates the typical progression of a feature requirement, demonstrating the interaction between the Functional Modules and the Flowable Workflow Engine using the example of the LIMS system's "Urgent Test" feature.

### Step 1: Ideation and Drafting (Stakeholder / Requirement Owner)
- **Action**: The Material Examination Team (Stakeholder) meets and designates a team member (acting as the Requirement Owner) to document the need for the "Urgent Test" feature. The Requirement Owner navigates to the Requirement Management Page and creates a new requirement, providing a detailed description.
- **System**: The requirement is stored in the requirements table with current_state = 'DRAFT'. The owner sets the priority to 'Critical' and clicks "Submit for Review."
- **Workflow Trigger**: The Flowable Engine starts a new process instance for this feature.

### Step 2: Business & Departmental Review (Stakeholder Leader)
- **Action**: The Flowable BPMN definition dictates that departmental requirements must first be approved by the Test Department Leader (acting as a departmental manager role). This triggers a User Task for the Test Leader.
- **Notification**: The Test Leader receives a notification via the in-app Dashboard Widget and email.
- **Decision**: The Test Leader reviews the requirement (REQ-XXX) via the Audit Page and ensures it aligns with business goals, then clicks "Approve."
- **System Update**: Flowable moves the process forward, updating the current_state to 'READY_FOR_DEV_REVIEW'.

### Step 3: Technical Review, Meeting, and Scheduling (Dev Team Lead)
- **Action**: Flowable creates a User Task assigned to the LIMS Development Team Lead. The Dev Lead uses the requirement's unique AXEL ID (REQ-XXX) to pull up the detailed specs.
- **Collaboration**: The Dev Lead, UI Designer, Frontend, and Backend developers meet with the Test Team. They use AXEL's Create/Edit Interface to make simultaneous changes to the description, adding technical notes, linking to design documents, and refining the estimated hours (estimated_hours).
- **Scheduling**: The Dev Lead uses the Scheduling Page to visualize the impact. They agree on a timeline and drag the newly refined mission onto a specific Developer's timeline.
- **Decision**: Once the plan is agreed upon, the Dev Lead clicks "Technical Approval." This updates the mission assignment in the missions table and sets the requirements.current_state to 'IN_PROGRESS'.

### Step 4: Development and Submission (Developer)
- **Action**: The assigned Developer works on the mission, tracking progress in their Dashboard Widget.
- **Workflow**: Upon completion, the Developer clicks "Submit Mission," optionally providing a link to the Pull Request or a screenshot in the comments section.
- **System Update**: Flowable moves the token to the final acceptance gate. The mission status in the missions table is updated to 'COMPLETED'.

### Step 5: Final Audit and Acceptance (Requirement Owner)
- **Action**: The original Requirement Owner (from the Test Team) is immediately notified by the system that the work is ready for review. They check the live feature in the LIMS system.
- **Decision Controls**:
  - **Happy Path**: If the feature meets the original goal, the Requirement Owner clicks "Accept/Approve Completion."
  - **Rework Path**: If changes are needed, they click "Reject," provide comments, and Flowable automatically routes the task back to the assigned Developer with status reset to 'IN_PROGRESS'.
  - **Relational Action**: If the rejection leads to a fundamentally new requirement (e.g., a scope change), the Requirement Owner closes the current feature and starts a new one (Step 1).

### Step 6: Closure and Reporting (System)
- **Action**: Upon final approval, Flowable terminates the process instance, setting requirements.current_state to 'DONE'.
- **Reporting**: The Report Module automatically captures the total cycle time (from Step 1 to Step 6), ensuring this critical metric is logged for performance analysis. The feature is now visible as "Done" on the Program Management Page.
