import { Component } from 'react'
import ErrorMessage from './component/ui/error/ErrorMessage'

class ErrorBoundary extends Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    static getDerivedStateFromError(error) {
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        console.error('Error caught by ErrorBoundary:', error);
        console.error('Error stack info:', errorInfo);
    }

    render() {
        return this.state.hasError ? <ErrorMessage /> : this.props.children;
    }
}

export default ErrorBoundary;