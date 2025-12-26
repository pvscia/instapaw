import Modal from "./Modal";

export default function ModalConfirmation(props) {
  const { message, handleCloseModal, handleConfirm } = props;

  return (
    <Modal handleCloseModal={handleCloseModal}>
      <div className="flex flex-col h-full text-center">
        <h1 className="text-lg font-semibold p-4">
          {message}
        </h1>
        <div className="flex ">
          <button
            onClick={handleConfirm}
            className="mt-auto self-center text-white border-2 px-5 py-2 rounded-full bg-red-500 w-full"
          >
            Yes
          </button>

          <button
            onClick={handleCloseModal}
            className="mt-auto self-center text-red-500 border-2 px-5 py-2 rounded-full bg-white w-full"
          >
            Cancel
          </button>

        </div>
      </div>
    </Modal>
  );
}
