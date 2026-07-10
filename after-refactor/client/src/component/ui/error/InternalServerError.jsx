import React from 'react';
import internalServerErrorImage from '../../../images/internal-server-500.png';
import { ErrorImage } from './ErrorImage';

function InternalServerError() {
    return <ErrorImage image={internalServerErrorImage} alt="internal-server-error-image" />;
}

export default InternalServerError;