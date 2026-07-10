import { useEffect } from 'react';

export function useClickAway(ref, onClose) {
  useEffect(() => {
    function handleClickOutside(event) {
      if (!ref.current) return;

      const isClickOnBackdrop = event.target.getAttribute('class')?.includes('MuiBackdrop-root');
      const isClickOutside = !ref.current.contains(event.target);

      if (isClickOnBackdrop || isClickOutside) {
        onClose();
      }
    }

    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [ref, onClose]);
}