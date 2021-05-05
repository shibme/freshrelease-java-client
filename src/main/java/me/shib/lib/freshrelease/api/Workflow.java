package me.shib.lib.freshrelease.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Workflow {

    private String project_label;
    private int id;
    private String name;
    private String label;
    private String description;
    private long default_status_id;
    private List<Long> transition_ids;
    private boolean editable;
    private List<WorkflowStep> workflow_steps;
    @SerializedName("default")
    private boolean defaultWorkflow;
    private List<Long> group_ids;
    private List<Long> status_ids;
    private List<Long> va_rule_ids;

    public String getProject_label() {
        return project_label;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public long getDefault_status_id() {
        return default_status_id;
    }

    public List<Long> getTransition_ids() {
        return transition_ids;
    }

    public boolean isEditable() {
        return editable;
    }

    public List<WorkflowStep> getWorkflow_steps() {
        return workflow_steps;
    }

    public boolean isDefaultWorkflow() {
        return defaultWorkflow;
    }

    public List<Long> getGroup_ids() {
        return group_ids;
    }

    public List<Long> getStatus_ids() {
        return status_ids;
    }

    public List<Long> getVa_rule_ids() {
        return va_rule_ids;
    }

    public class WorkflowStep {
        private String id;
        private int category_id;
        private long from_status_id;
        private List<Long> to_status_ids;
        private List<String> groups;
        private List<Long> va_rule_ids;
        private List<String> required_attributes;
        private boolean resolve_sub_items;

        public String getId() {
            return id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public long getFrom_status_id() {
            return from_status_id;
        }

        public List<Long> getTo_status_ids() {
            return to_status_ids;
        }

        public List<String> getGroups() {
            return groups;
        }

        public List<Long> getVa_rule_ids() {
            return va_rule_ids;
        }

        public List<String> getRequired_attributes() {
            return required_attributes;
        }

        public boolean isResolve_sub_items() {
            return resolve_sub_items;
        }
    }

}
