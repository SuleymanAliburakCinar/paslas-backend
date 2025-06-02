package com.paslas.backend.util;

import com.paslas.backend.entity.Participation;

public class ParticipationFactory {

    public static Participation createStatusDeclinedParticipation(){
        Participation participation = new Participation();
        participation.setStatus(Participation.ParticipationStatus.DECLINED);
        return participation;
    }

    public static Participation createStatusConfirmedParticipation(){
        Participation participation = new Participation();
        participation.setStatus(Participation.ParticipationStatus.CONFIRMED);
        return participation;
    }
    public static Participation createStatusWaitlistedParticipation(){
        Participation participation = new Participation();
        participation.setStatus(Participation.ParticipationStatus.WAITLISTED);
        return participation;
    }
}
