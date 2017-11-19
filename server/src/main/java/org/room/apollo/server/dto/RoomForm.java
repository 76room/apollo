package org.room.apollo.server.dto;

import org.room.apollo.server.entity.Room;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static org.room.apollo.server.utils.RoomValidationConstants.*;

public class RoomForm {

    @NotNull
    @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH)
    private String title;

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_DESCRIPTION_LENGTH)
    private String description;

    private Room.Status status = Room.Status.PUBLIC;

    public RoomForm() {
    }

    public RoomForm(String title, String description, Room.Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room.Status getStatus() {
        return status;
    }

    public void setStatus(Room.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomForm{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomForm roomForm = (RoomForm) o;

        if (!title.equals(roomForm.title)) return false;
        if (description != null ? !description.equals(roomForm.description) : roomForm.description != null)
            return false;
        return status == roomForm.status;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
