import Modal from "./Modal";

export default function ModalStatus(props) {
  const { message, handleCloseModal } = props;

  return (
    <Modal handleCloseModal={handleCloseModal}>
      <div className="flex flex-col h-full text-center">
        <h1 className="text-lg font-semibold p-4">
          {message}
        </h1>

        <button
          onClick={handleCloseModal}
          className="mt-auto self-center text-white border-2 px-5 py-2 rounded-full bg-red-500"
        >
          OK
        </button>
      </div>
    </Modal>
  );
}
