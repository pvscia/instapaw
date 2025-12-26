import { useEffect, useState } from "react";
import Modal from "./Modal";

export default function ModalComplaint(props) {
  const { complaint: complaintObj, handleCloseModal, handleSubmit } = props;
  const [complaint, setComplaint] = useState("");

  useEffect(() => {
    if (complaintObj) {
      setComplaint(complaintObj.complaint)
    }
  }, [complaintObj])
  return (
    <Modal handleCloseModal={handleCloseModal}>
      <div className="flex flex-col h-full text-center">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            if (!complaint || complaint.trim() === "") {
              alert("Must fill complaint");
            } else {
              handleSubmit(complaint);
            }

          }}
        >

          <textarea
            value={complaint}
            className="w-full border p-2 mb-3 rounded resize-none"
            placeholder="Complaint"
            rows={4}
            onChange={(e) => setComplaint(e.target.value)}
          />

          <div className="flex ">
            <button
              type="submit"
              className="mt-auto self-center text-white border-2 px-5 py-2 rounded-full bg-red-500 w-full"
            >
              {complaintObj ? "Edit" : "Submit"}
            </button>

            <button
              type="button"
              onClick={handleCloseModal}
              className="mt-auto self-center text-red-500 border-2 px-5 py-2 rounded-full bg-white w-full"
            >
              Cancel
            </button>

          </div>
        </form>
      </div>
    </Modal>
  );
}
