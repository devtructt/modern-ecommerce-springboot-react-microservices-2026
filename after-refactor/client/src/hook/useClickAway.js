import { useEffect, useCallback } from "react";

const useClickAway = closeHandler => {
  const handleClickAway = useCallback(event => {
    if (event.target.closest('.MuiBackdrop-root')) {
      closeHandler();
    }
  }, [closeHandler])

  useEffect(() => {
    document.addEventListener('pointerdown', handleClickAway);
    return () => {
      document.removeEventListener('pointerdown', handleClickAway);
    };
  }, [handleClickAway]);
}

export default useClickAway;;