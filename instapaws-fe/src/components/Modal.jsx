import ReactDom from 'react-dom'

export default function Modal(props) {
    const { children, handleCloseModal } = props
    return ReactDom.createPortal(
        <div className='fixed top-0 left-0 h-screen w-screen flex flex-col items-center justify-center z-[100] p-4'>
            <button onClick={handleCloseModal} className='absolute inset-0 w-full bg-gray-200 opacity-80 z-[99] border-0 shadow-none' />
            <div className='relative z-[101] max-w-[400px] w-full mx-auto rounded-lg border bg-white p-4 flex flex-col gap-4'>
                {children}
            </div>

        </div>,

        document.getElementById('portal')
    )
}