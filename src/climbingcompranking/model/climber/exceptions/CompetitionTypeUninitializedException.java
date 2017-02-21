/*
 * Copyright 2017 Hugo Da Roit - contact@hdaroit.fr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package climbingcompranking.model.climber.exceptions;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class CompetitionTypeUninitializedException extends Exception {
    public CompetitionTypeUninitializedException() {
        super();
    }
    
    public CompetitionTypeUninitializedException(String message) {
        super(message);
    }
    
    public CompetitionTypeUninitializedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CompetitionTypeUninitializedException(Throwable cause) {
        super(cause);
    }       
}
